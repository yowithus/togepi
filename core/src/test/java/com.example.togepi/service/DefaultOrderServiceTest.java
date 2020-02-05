package com.example.togepi.service;

import com.example.togepi.entity.Item;
import com.example.togepi.entity.Order;
import com.example.togepi.dao.OrderDao;
import com.example.togepi.service.impl.DefaultOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultOrderService.class)
public class DefaultOrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderDao orderDao;

    @Test
    public void givenValidItems_whenCreatingOrderAndCalculatingGrandTotal_thenReturnCorrectResult() throws Exception {
        final List<Item> items = new ArrayList<>();
        items.add(new Item("Test item", "1", BigDecimal.valueOf(276)));
        items.add(new Item("Test item2", "2", BigDecimal.valueOf(555)));
        items.add(new Item("Test item3", "3", BigDecimal.valueOf(101)));
        final Order order = new Order(items);

        when(orderDao.save(order)).thenReturn(order);

        final Order orderResult = orderService.createOrder(order);
        final BigDecimal grandTotalResult = BigDecimal.valueOf(980.71);

        assertEquals(grandTotalResult, orderResult.getGrandTotal());
    }

    @Test
    public void givenValidItems_whenGettingOrderAndCalculatingGrandTotal_thenReturnCorrectResult() throws Exception {
        final List<Item> items = new ArrayList<>();
        items.add(new Item("Test item", "1", BigDecimal.valueOf(276)));
        items.add(new Item("Test item2", "2", BigDecimal.valueOf(555)));
        items.add(new Item("Test item3", "3", BigDecimal.valueOf(101)));
        final Order order = new Order(items);
        final Long orderId = Long.valueOf(1);

        when(orderDao.findById(orderId)).thenReturn(Optional.ofNullable(order));

        final Order orderResult = orderService.getOrderById(orderId);
        final BigDecimal grandTotalResult = BigDecimal.valueOf(980.71);

        assertEquals(grandTotalResult, orderResult.getGrandTotal());
    }
}
