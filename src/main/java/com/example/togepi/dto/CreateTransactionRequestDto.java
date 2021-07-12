package com.example.togepi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransactionRequestDto {

    private Long userId;
    private BigDecimal amount;
    private String receiptNo;
}
