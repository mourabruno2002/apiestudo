package com.example.apiestudo.dto.product;

import jakarta.validation.constraints.NotNull;

public class ProductStockDTO {

    @NotNull(message = "StockQuantity is required.")
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
