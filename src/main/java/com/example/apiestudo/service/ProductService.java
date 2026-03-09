package com.example.apiestudo.service;

import com.example.apiestudo.dto.product.*;
import com.example.apiestudo.exception.domain.category.CategoryNotFoundException;
import com.example.apiestudo.exception.domain.product.DuplicateSkuException;
import com.example.apiestudo.exception.domain.product.ProductNotFoundException;
import com.example.apiestudo.mapper.ProductMapper;
import com.example.apiestudo.model.Category;
import com.example.apiestudo.model.Product;
import com.example.apiestudo.repository.CategoryRepository;
import com.example.apiestudo.repository.ProductRepository;
import com.example.apiestudo.specification.ProductSpecification;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
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

        return productMapper.convertProductToResponse(getById(id));
    }

    public Page<ProductResponseDTO> search(ProductFilterDTO productFilterDTO, Pageable pageable) {
        if (productFilterDTO.getPriceMin() != null && productFilterDTO.getPriceMax() != null && productFilterDTO.getPriceMin().compareTo(productFilterDTO.getPriceMax()) > 0) {
            throw new IllegalArgumentException("priceMin cannot be greater than priceMax");
        }

        Specification<Product> spec = Specification.where(ProductSpecification.fetchCategory());
        spec = spec.and(ProductSpecification.nameContaining(productFilterDTO.getName()));
        spec = spec.and(ProductSpecification.hasSku(productFilterDTO.getSku()));
        spec = spec.and(ProductSpecification.isActive(productFilterDTO.getActive()));
        spec = spec.and(ProductSpecification.categoryNameContaining(productFilterDTO.getCategoryName()));
        spec = spec.and(ProductSpecification.findByPrice(productFilterDTO.getPriceMin(), productFilterDTO.getPriceMax()));

        return productRepository.findAll(spec, pageable).map(productMapper::convertProductToResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ProductResponseDTO updateActive(Long id, ProductActiveDTO productActiveDTO) {
        Product product = getById(id);

        product.setActive(productActiveDTO.getActive());

        return productMapper.convertProductToResponse(productRepository.save(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ProductResponseDTO updateStock(Long id, ProductStockDTO productStockDTO) {
        Product product = getById(id);

        product.setStockQuantity(productStockDTO.getStockQuantity());

        return productMapper.convertProductToResponse(productRepository.save(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
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
