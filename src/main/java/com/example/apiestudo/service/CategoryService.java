package com.example.apiestudo.service;

import com.example.apiestudo.dto.category.CategoryRequestDTO;
import com.example.apiestudo.dto.category.CategoryResponseDTO;
import com.example.apiestudo.exception.category.CategoryAlreadyExistsException;
import com.example.apiestudo.mapper.CategoryMapper;
import com.example.apiestudo.model.Category;
import com.example.apiestudo.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {
        if (categoryRepository.existsByName(categoryRequestDTO.getName())) {
            throw  new CategoryAlreadyExistsException("A category with this name already exists.");
        }

        Category category = categoryMapper.convertRequestToCategory(categoryRequestDTO);
        category.setActive(true);

        return categoryMapper.convertCategoryToResponse(categoryRepository.save(category));
    }
}
