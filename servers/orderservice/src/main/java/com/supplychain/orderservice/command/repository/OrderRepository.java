package com.supplychain.orderservice.command.repository;

import com.supplychain.orderservice.command.data.request.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findBySenderId(String senderId);
    Order getOrderByRequestId(String requestId);
}

