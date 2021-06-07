package com.example.togepi.batch;

import com.example.togepi.dto.csv.OrderCsvDto;
import org.springframework.batch.item.ItemProcessor;

public class OrderCsvItemProcessor implements ItemProcessor<OrderCsvDto, OrderCsvDto> {

    @Override
    public OrderCsvDto process(final OrderCsvDto orderCsvDto) throws Exception {
        return orderCsvDto;
    }
}
