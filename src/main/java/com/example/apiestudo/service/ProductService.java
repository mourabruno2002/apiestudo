package com.example.apiestudo.service;

import com.example.apiestudo.dto.product.ProductActiveDTO;
import com.example.apiestudo.dto.product.ProductRequestDTO;
import com.example.apiestudo.dto.product.ProductResponseDTO;
import com.example.apiestudo.dto.product.ProductUpdateDTO;
import com.example.apiestudo.exception.FieldRequiredException;
import com.example.apiestudo.exception.category.CategoryNotFoundException;
import com.example.apiestudo.exception.product.DuplicateSkuException;
import com.example.apiestudo.exception.product.ProductNotFoundException;
import com.example.apiestudo.mapper.ProductMapper;
import com.example.apiestudo.model.Category;
import com.example.apiestudo.model.Product;
import com.example.apiestudo.repository.CategoryRepository;
import com.example.apiestudo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        if (productRepository.existsBySku(productRequestDTO.getSku())) {
            throw new DuplicateSkuException("SKU already in use.");
        }
        Product product = productMapper.convertRequestToProduct(productRequestDTO);
        Category category = findCategoryById(productRequestDTO.getCategoryId());

        product.setCategory(category);
        product.setActive(true);

        return productMapper.convertProductToResponse(productRepository.save(product));
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found.")
        );

        return productMapper.convertProductToResponse(product);
    }

    public Page<ProductResponseDTO> findAll(Pageable pageable) {

        return productRepository.findAll(pageable).map(productMapper::convertProductToResponse);
    }

    @Transactional
    public ProductResponseDTO update(Long id, ProductUpdateDTO productUpdateDTO) {
        Product product = getById(id);

        if (productUpdateDTO.getName() != null) {
            product.setName(productUpdateDTO.getName());
        }
        if (productUpdateDTO.getDescription() != null) {
            product.setDescription(productUpdateDTO.getDescription());
        }
        if (productUpdateDTO.getPrice() != null) {
            product.setPrice(productUpdateDTO.getPrice());
        }
        if (productUpdateDTO.getImageUrl() != null) {
            product.setImageUrl(productUpdateDTO.getImageUrl());
        }

        if (productUpdateDTO.getSku() != null) {
            if (!Objects.equals(productUpdateDTO.getSku(), product.getSku())) {

                if (productRepository.existsBySkuAndIdNot(productUpdateDTO.getSku(), product.getId())) {
                    throw new DuplicateSkuException("SKU already in use.");
                }

                product.setSku(productUpdateDTO.getSku());
            }
        }

        if (productUpdateDTO.getCategoryId() != null) {
            Category category = findCategoryById(productUpdateDTO.getCategoryId());

            product.setCategory(category);
        }

        return productMapper.convertProductToResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDTO updateActive(Long id, ProductActiveDTO productActiveDTO) {
        Product product = getById(id);

        if (productActiveDTO.getActive() == null) {
            throw new FieldRequiredException("Field active is required.");
        }

        product.setActive(productActiveDTO.getActive());

        return productMapper.convertProductToResponse(productRepository.save(product));
    }

    @Transactional
    public void deleteById(Long id) {
        getById(id);

        productRepository.deleteById(id);
    }

    // INTERNAL METHODS
    private Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found.")
        );
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found.")
        );
    }
}
