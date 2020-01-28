package com.example.togepi.mapper;

import com.example.togepi.entity.Order;
import com.example.togepi.dto.csv.OrderCsvDto;
import com.example.togepi.enums.OrderStatus;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class OrderCsvMapper extends CustomMapper<Order, OrderCsvDto> {

    @Override
    public void mapAtoB(Order a, OrderCsvDto b, MappingContext context) {
        b.setId(a.getId());
        b.setStatus(a.getStatus().getValue());
        b.setGrandTotal(a.getGrandTotal());
    }

    @Override
    public void mapBtoA(OrderCsvDto a, Order b, MappingContext context) {
        b.setId(a.getId());
        b.setStatus(OrderStatus.valueOf(a.getStatus()));
        b.setGrandTotal(a.getGrandTotal());
    }
}
