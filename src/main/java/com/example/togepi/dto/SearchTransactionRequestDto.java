package com.example.togepi.dto;

import lombok.Data;

@Data
public class SearchTransactionRequestDto {

    private Long userId;
    private String receiptNo;
}
