package com.example.togepi.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction extends BaseEntity {

    private Long userId;
    private BigDecimal amount;
    private String receiptNo;
}
