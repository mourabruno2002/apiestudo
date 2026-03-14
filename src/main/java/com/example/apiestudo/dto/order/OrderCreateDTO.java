package com.example.apiestudo.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class OrderCreateDTO {

    @NotNull(message = "userId is required.")
    @Min(value = 1, message = "userId must be greater than zero.")
    private Long userId;

    @Size(min = 1, message = "Order must contain at least one item.")
    @Valid
    private List<OrderItemCreateDTO> items = new ArrayList<>();

    public Long getUserId() {
        return userId;
    }

    public List<OrderItemCreateDTO> getItems() {
        return items;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setItems(List<OrderItemCreateDTO> items) {
        this.items = items;
    }
}
