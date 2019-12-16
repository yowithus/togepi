package com.example.togepi.controller;

import com.example.togepi.entity.Order;
import com.example.togepi.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(tags = "Order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/orders")
    @ApiOperation(value = "Get all orders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping(value = "/orders/{orderId}")
    @ApiOperation(value = "Get an order by id")
    public Order getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PostMapping(value = "/orders")
    @ApiOperation(value = "Create an order")
    public Order createOrder(@Valid @RequestBody Order order) {
        return orderService.createOrder(order);
    }
//
//    @PutMapping(value = "/orders/{orderId}")
//    public Order updateOrder(@PathVariable Long orderId, @Valid @RequestBody Order order) {
//        return mainService.updateOrder(orderId, order);
//    }
//
//    @DeleteMapping(value = "/orders/{orderId}")
//    public Map<String, Boolean> deleteOrder(@PathVariable Long orderId) {
//        return mainService.deleteOrders(orderId);
//    }
}
