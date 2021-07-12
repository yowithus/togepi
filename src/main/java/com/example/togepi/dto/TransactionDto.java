package com.example.togepi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class TransactionDto {

    private UUID id;
    private Long userId;
    private BigDecimal amount;
    private String receiptNo;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
