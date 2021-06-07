package com.example.togepi.service.impl;

import com.example.togepi.enums.Category;
import com.example.togepi.enums.OrderStatus;
import com.example.togepi.exception.ResourceNotFoundException;
import com.example.togepi.entity.Item;
import com.example.togepi.entity.Order;
import com.example.togepi.dao.OrderDao;
import com.example.togepi.service.OrderService;
import com.example.togepi.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service()
public class DefaultOrderService implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order createOrder(Order order) {
        return orderDao.save(setOrderDetail(order));
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderDao.findById(orderId)
                .map(this::setOrderDetail)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<Order> getOrders() {
        return orderDao.findAll()
                .stream()
                .filter(isStatusPending())
                .map(this::setOrderDetail)
                .collect(Collectors.toList());
    }

    private Order setOrderDetail(Order order) {
        final List<Item> items = order.getItems();
        items.stream().forEach(item -> {
            final BigDecimal price = item.getPrice();
            final String categoryId = item.getCategoryId();
            final Category category = Category.findById(categoryId);
            final String categoryName = category.getName();
            final BigDecimal tax = CommonUtil.calculateTax(item);
            final BigDecimal amount = item.getPrice().add(tax);

            item.setTax(tax);
            item.setAmount(amount);
            item.setCategoryName(categoryName);

            order.setPriceSubtotal(order.getPriceSubtotal().add(price));
            order.setTaxSubtotal(order.getTaxSubtotal().add(tax));
            order.setGrandTotal(order.getGrandTotal().add(amount));
            order.setStatus(OrderStatus.PENDING);
        });

        return order;
    }

    private Predicate<Order> isStatusPending() {
        return order -> StringUtils.equalsIgnoreCase(order.getStatus().getValue(), OrderStatus.PENDING.getValue());
    }
}
