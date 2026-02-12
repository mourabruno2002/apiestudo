package com.example.apiestudo.mapper;

import com.example.apiestudo.dto.product.ProductRequestDTO;
import com.example.apiestudo.dto.product.ProductResponseDTO;
import com.example.apiestudo.model.Product;
import com.example.apiestudo.utils.MapperService;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class ProductMapper {

    private final MapperService mapperService;

    public ProductMapper(MapperService mapperService) {
        this.mapperService = mapperService;
    }

    public Product convertRequestToProduct(ProductRequestDTO productRequestDTO) {
        return mapperService.map(productRequestDTO, Product.class);
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
