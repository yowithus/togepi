package com.example.togepi.controller;

import com.example.togepi.exception.ResourceNotFoundException;
import com.example.togepi.entity.Item;
import com.example.togepi.entity.Order;
import com.example.togepi.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    public void givenValidItems_whenCreatingOrder_thenReturnSuccess() throws Exception {
        final List<Item> items = new ArrayList<>();
        items.add(new Item("Test item", "1", BigDecimal.valueOf(500)));
        items.add(new Item("Test item2", "2", BigDecimal.valueOf(500)));
        final Order order = new Order(items);

        when(orderService.createOrder(order)).thenReturn(order);
        this.mockMvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenEmptyItem_whenCreatingOrder_thenReturnBadRequest() throws Exception {
        final List<Item> items = new ArrayList<>();
        final Order order = new Order(items);

        this.mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenGettingExistingOrder_thenReturnSuccess() throws Exception {
        final Long orderId = Long.valueOf(1);

        when(orderService.getOrderById(orderId)).thenReturn(new Order());
        this.mockMvc.perform(get("/orders/" + orderId))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGettingNonExistingOrder_thenReturnNotFound() throws Exception {
        final Long orderId = Long.valueOf(2);

        when(orderService.getOrderById(orderId)).thenThrow(new ResourceNotFoundException("Order not found"));
        this.mockMvc.perform(get("/orders/" + orderId))
                .andExpect(status().isNotFound());
    }
}