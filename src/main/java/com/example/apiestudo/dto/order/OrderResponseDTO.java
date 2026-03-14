package com.example.apiestudo.dto.order;

import com.example.apiestudo.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderResponseDTO {

    private final String orderNumber;

    private final String customerName;

    private final String customerEmail;

    private final String customerCpf;

    private final String customerPhone;

    private final OrderStatus status;

    private final BigDecimal shipping;

    private final BigDecimal subtotal;

    private final BigDecimal discount;

    private final BigDecimal total;

    private List<OrderItemResponseDTO> items = new ArrayList<>();

    private final Instant createdAt;
    //endregion


    public OrderResponseDTO(String orderNumber, String customerName, String customerEmail, String customerCpf,
                            String customerPhone, OrderStatus status, BigDecimal shipping, BigDecimal subtotal,
                            BigDecimal discount, BigDecimal total, List<OrderItemResponseDTO> items, Instant createdAt) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerCpf = customerCpf;
        this.customerPhone = customerPhone;
        this.status = status;
        this.shipping = shipping;
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
        this.items = items;
        this.createdAt = createdAt;
    }
}
