package com.example.apiestudo.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductUpdateDTO {

    @Size(max = 100, message = "Name must be shorter than 100 characters long.")
    private String name;

    @Size(max = 255, message = "Description must be shorter than 255 characters.")
    private String description;

    @Size(max = 50, message = "SKU must be shorter than 50 characters.")
    private String sku;

    @DecimalMin("0.01")
    private BigDecimal price;

    private String imageUrl;

    private Long categoryId;


    // GETTERS
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
