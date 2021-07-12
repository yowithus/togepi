package com.example.togepi.service;

import com.example.togepi.dto.CreateTransactionRequestDto;
import com.example.togepi.dto.SearchTransactionRequestDto;
import com.example.togepi.dto.TransactionDto;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    void create(CreateTransactionRequestDto createTransactionRequestDto);

    TransactionDto findById(UUID id);

    List<TransactionDto> search(SearchTransactionRequestDto searchTransactionRequestDto);
}
