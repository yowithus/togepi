package com.example.togepi.service;

import com.example.togepi.dao.OrderDao;
import com.example.togepi.dto.csv.OrderCsvDto;
import com.example.togepi.entity.Order;
import com.example.togepi.enums.OrderStatus;
import com.example.togepi.mapper.MainMapper;
import com.example.togepi.service.impl.DefaultOpenCsvService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultOpenCsvService.class)
public class DefaultOpenCsvServiceTest {

    @Autowired
    private OpenCsvService openCsvService;

    @MockBean
    private MainMapper mapper;

    @MockBean
    private OrderDao orderDao;

    @Test
    public void givenValidOrders_afterWritingToCsv_thenReturnCorrectResult() throws Exception {
        final Order order = new Order();
        order.setId(1L);
        order.setStatus(OrderStatus.PENDING);
        order.setGrandTotal(BigDecimal.valueOf(5000));
    
        final OrderCsvDto orderCsvDto = new OrderCsvDto();
        orderCsvDto.setId(order.getId());
        orderCsvDto.setStatus(order.getStatus().getValue());
        orderCsvDto.setGrandTotal(order.getGrandTotal());
        
        final List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderDao.findAll()).thenReturn(orders);
        when(mapper.map(order, OrderCsvDto.class)).thenReturn(orderCsvDto);
        
        openCsvService.writeFromObject();

        final List<Order> resOrders = openCsvService.readToObject();
        assertEquals(resOrders.size(), orders.size());
    }
}
