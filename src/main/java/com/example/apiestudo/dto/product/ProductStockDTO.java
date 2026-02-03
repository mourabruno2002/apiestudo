package com.example.apiestudo.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductStockDTO {

    @NotNull(message = "The field 'stockQuantity' is required.")
    @Min(value = 0, message = "The field 'stockQuantity' cannot be negative.")
    private Integer stockQuantity;

    // GETTER
    public Integer getStockQuantity() {
        return stockQuantity;
    }

    // SETTER
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
