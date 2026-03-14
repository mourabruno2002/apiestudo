package com.example.apiestudo.mapper;

import com.example.apiestudo.GenericConverter;
import com.example.apiestudo.dto.order.OrderCreateDTO;
import com.example.apiestudo.dto.order.OrderItemResponseDTO;
import com.example.apiestudo.dto.order.OrderResponseDTO;
import com.example.apiestudo.model.Order;
import com.example.apiestudo.model.OrderItem;

public class OrderMapper {

    private final GenericConverter genericConverter;

    public OrderMapper(GenericConverter genericConverter) {
        this.genericConverter = genericConverter;
    }

    public OrderResponseDTO convertOrderToResponse(Order order) {

        return genericConverter.map(order, OrderResponseDTO.class);
    }

    public OrderItemResponseDTO convertOrderItemToResponse(OrderItem orderItem) {

        return genericConverter.map(orderItem, OrderItemResponseDTO.class);
    }

}
