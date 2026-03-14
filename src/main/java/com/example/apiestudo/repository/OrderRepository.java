package com.example.apiestudo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.apiestudo.model.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

}
