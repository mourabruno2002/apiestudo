package com.example.apiestudo.service;

import com.example.apiestudo.dto.order.OrderCreateDTO;
import com.example.apiestudo.dto.order.OrderItemCreateDTO;
import com.example.apiestudo.dto.order.OrderResponseDTO;
import com.example.apiestudo.exception.domain.product.ProductNotFoundException;
import com.example.apiestudo.exception.domain.user.UserNotFoundException;
import com.example.apiestudo.mapper.OrderMapper;
import com.example.apiestudo.model.Order;
import com.example.apiestudo.model.Product;
import com.example.apiestudo.model.User;
import com.example.apiestudo.repository.OrderRepository;
import com.example.apiestudo.repository.ProductRepository;
import com.example.apiestudo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository, UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponseDTO create(OrderCreateDTO orderCreateDTO) {

        User user = userRepository.findById(orderCreateDTO.getUserId()).orElseThrow(
                () -> new UserNotFoundException(String.format("User not found. Id: %d", orderCreateDTO.getUserId()))
        );

        Order newOrder = Order.create(user);
        List<Long> productsIds = orderCreateDTO.getItems().stream().map(OrderItemCreateDTO::getProductId).toList();

        List<Product> products = productRepository.findAllById(productsIds);

        Map<Long, Integer> orderItems = orderCreateDTO.getItems().stream().collect(Collectors.toMap(
                OrderItemCreateDTO::getProductId, OrderItemCreateDTO::getQuantity));



        return orderMapper.convertOrderToResponse(orderRepository.save(newOrder));
    }
}
