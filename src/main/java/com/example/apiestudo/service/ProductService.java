package com.example.apiestudo.service;

import com.example.apiestudo.dto.product.ProductRequestDTO;
import com.example.apiestudo.dto.product.ProductResponseDTO;
import com.example.apiestudo.dto.product.ProductUpdateDTO;
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
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException("Category not found.")
        );

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

    public ProductResponseDTO update(Long id, ProductUpdateDTO productUpdateDTO) {
        Product product = productRepository.findById(id);
    }
}
