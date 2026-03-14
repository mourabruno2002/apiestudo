package com.example.apiestudo.mapper;

import com.example.apiestudo.dto.category.CategoryRequestDTO;
import com.example.apiestudo.dto.category.CategoryResponseDTO;
import com.example.apiestudo.model.Category;
import com.example.apiestudo.GenericConverter;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class CategoryMapper {

    private final GenericConverter genericConverter;

    public CategoryMapper(GenericConverter genericConverter) {
        this.genericConverter = genericConverter;
    }

    public Category convertRequestToCategory(CategoryRequestDTO categoryRequestDTO) {
        return genericConverter.map(categoryRequestDTO, Category.class);
    }

    public CategoryResponseDTO convertCategoryToResponse(Category category) {

        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isActive(),
                category.getCreatedAt().atZone(ZoneOffset.UTC).toInstant(),
                category.getUpdatedAt().atZone(ZoneOffset.UTC).toInstant()
        );
    }
}
