package com.example.apiestudo.dto.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductRequestDTO {

    @NotBlank(message = "Name is required.")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters long.")
    private String name;

    @Size(max = 255, message = "Description must be short than 255 characters.")
    private String description;

    @NotBlank(message = "SKU is required.")
    @Size(max = 50, message = "SKU must be shorter than 50 characters.")
    private String sku;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.01", message = "Price cannot be negative or zero.")
    private BigDecimal price;

    @Min(value = 0, message = "Stock quantity cannot be negative.")
    private int stockQuantity;

    private String imageUrl;

    @NotNull(message = "Category is required.")
    @Min(value = 1, message = "Category ID must be greater than or equal to 1.")
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

    public int getStockQuantity() {
        return stockQuantity;
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

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
