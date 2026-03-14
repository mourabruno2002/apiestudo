package com.example.apiestudo.dto.order;

import java.math.BigDecimal;

public class OrderItemResponseDTO {

    private String productName;

    private String sku;

    private BigDecimal unitPrice;

    private int quantity;

    private BigDecimal discountPerUnit;

    public OrderItemResponseDTO(String productName, String sku, BigDecimal unitPrice, int quantity, BigDecimal discountPerUnit) {
        this.productName = productName;
        this.sku = sku;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.discountPerUnit = discountPerUnit;
    }
}
