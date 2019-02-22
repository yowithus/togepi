package com.example.togepi.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CSVExpense {

    @CsvBindByPosition(position = 0)
    private String name;

    @CsvBindByPosition(position = 1)
    private String description;

    @CsvBindByPosition(position = 2)
    private BigDecimal amount;
}
