package com.example.apiestudo.service;

import com.example.apiestudo.dto.category.CategoryActiveDTO;
import com.example.apiestudo.dto.category.CategoryRequestDTO;
import com.example.apiestudo.dto.category.CategoryResponseDTO;
import com.example.apiestudo.dto.category.CategoryUpdateDTO;
import com.example.apiestudo.exception.domain.category.CategoryAlreadyExistsException;
import com.example.apiestudo.exception.domain.category.CategoryNotFoundException;
import com.example.apiestudo.mapper.CategoryMapper;
import com.example.apiestudo.model.Category;
import com.example.apiestudo.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {
        if (categoryRepository.existsByName(categoryRequestDTO.getName())) {
            throw  new CategoryAlreadyExistsException("A category with this name already exists.");
        }

        Category category = categoryMapper.convertRequestToCategory(categoryRequestDTO);
        category.setActive(true);

        return categoryMapper.convertCategoryToResponse(categoryRepository.save(category));
    }

    public CategoryResponseDTO findById(Long id) {

        return categoryMapper.convertCategoryToResponse(categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found.")
        ));
    }

    public Page<CategoryResponseDTO> findAll(Pageable pageable) {

        return categoryRepository.findAll(pageable).map(categoryMapper::convertCategoryToResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public CategoryResponseDTO update(Long id, CategoryUpdateDTO categoryUpdateDTO) {
        Category category = getById(id);

        if (categoryUpdateDTO.getName() != null) {
            if (!Objects.equals(categoryUpdateDTO.getName(), category.getName())) {

                if (categoryRepository.existsByNameAndIdNot(categoryUpdateDTO.getName(), id)) {
                    throw new CategoryAlreadyExistsException("A category with this name already exists.");
                }
            }

            category.setName(categoryUpdateDTO.getName());
        }

        if (categoryUpdateDTO.getDescription() != null) {
            category.setDescription(categoryUpdateDTO.getDescription());
        }

        return categoryMapper.convertCategoryToResponse(categoryRepository.save(category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public CategoryResponseDTO updateActive(Long id, CategoryActiveDTO categoryActiveDTO) {
        Category category = getById(id);

        category.setActive(categoryActiveDTO.getActive());

        return categoryMapper.convertCategoryToResponse(categoryRepository.save(category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteById(Long id) {
        getById(id);

        categoryRepository.deleteById(id);
    }

    // INTERNAL METHODS
    private Category getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found.")
        );
    }

}
