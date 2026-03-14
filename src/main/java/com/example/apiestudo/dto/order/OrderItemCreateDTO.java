package com.example.apiestudo.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemCreateDTO {

    @NotNull(message = "productID is required.")
    @Min(value = 1, message = "productId must be greater than zero.")
    private Long productId;

    @Min(value = 1, message = "quantity must be greater than zero.")
    private int quantity;

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
