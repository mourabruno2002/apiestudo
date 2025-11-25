package com.example.apiestudo.mapper;

import com.example.apiestudo.dto.product.ProductRequestDTO;
import com.example.apiestudo.dto.product.ProductResponseDTO;
import com.example.apiestudo.model.Product;
import com.example.apiestudo.utils.MapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final MapperUtils mapperUtils;

    public ProductMapper(MapperUtils mapperUtils) {
        this.mapperUtils = mapperUtils;
    }

    public Product convertRequestToProduct(ProductRequestDTO productRequestDTO) {
        return mapperUtils.map(productRequestDTO, Product.class);
    }

    public ProductResponseDTO convertProductToResponse(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSku(),
                product.getPrice(),
                product.getStockQuantity(),
                product.isActive(),
                product.getImageUrl(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
