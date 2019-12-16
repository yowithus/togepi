package com.example.togepi.service;

import com.example.togepi.entity.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    Order getOrderById(Long orderId);

    List<Order> getOrders();
}
