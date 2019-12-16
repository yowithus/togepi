package com.example.togepi.dto.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCsvDto {

    @CsvBindByPosition(position = 0)
    private Long id;

    @CsvBindByPosition(position = 1)
    private String status;
    
    @CsvBindByPosition(position = 2)
    private BigDecimal grandTotal;
}
