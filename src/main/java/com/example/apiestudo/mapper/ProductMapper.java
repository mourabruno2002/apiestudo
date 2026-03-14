package com.example.apiestudo.mapper;

import com.example.apiestudo.dto.product.ProductRequestDTO;
import com.example.apiestudo.dto.product.ProductResponseDTO;
import com.example.apiestudo.model.Product;
import com.example.apiestudo.GenericConverter;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class ProductMapper {

    private final GenericConverter genericConverter;

    public ProductMapper(GenericConverter genericConverter) {
        this.genericConverter = genericConverter;
    }

    public Product convertRequestToProduct(ProductRequestDTO productRequestDTO) {
        return genericConverter.map(productRequestDTO, Product.class);
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
                product.getCreatedAt().atZone(ZoneOffset.UTC).toInstant(),
                product.getUpdatedAt().atZone(ZoneOffset.UTC).toInstant()
        );
    }
}
