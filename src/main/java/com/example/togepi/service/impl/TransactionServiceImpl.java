package com.example.togepi.service.impl;

import com.example.togepi.constant.ElasticsearchConstant;
import com.example.togepi.constant.ErrorMessageConstant;
import com.example.togepi.dto.CreateTransactionRequestDto;
import com.example.togepi.dto.PaginationDto;
import com.example.togepi.dto.SearchTransactionRequestDto;
import com.example.togepi.dto.TransactionDto;
import com.example.togepi.entity.Transaction;
import com.example.togepi.exception.ClientException;
import com.example.togepi.repository.TransactionRepository;
import com.example.togepi.service.TransactionService;
import com.example.togepi.type.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void create(CreateTransactionRequestDto requestDto) {
        transactionRepository.upsert(createTransaction(requestDto));
    }

    @Override
    public TransactionDto findById(UUID id) {
        return transactionRepository.findById(id)
                .map(this::createTransactionDto)
                .orElseThrow(() -> new ClientException(ErrorType.TRANSACTION_NOT_FOUND, ErrorMessageConstant.TRANSACTION_NOT_FOUND, id));
    }

    @Override
    public List<TransactionDto> search(SearchTransactionRequestDto searchTransactionRequestDto) {
        final PaginationDto paginationDto = new PaginationDto("", ElasticsearchConstant.DEFAULT_PAGE_SIZE);

        return transactionRepository.search(paginationDto, searchTransactionRequestDto)
                .getResults().stream()
                .map(this::createTransactionDto)
                .collect(Collectors.toList());
    }

    private Transaction createTransaction(CreateTransactionRequestDto requestDto) {
        final Transaction transaction = new Transaction();
        transaction.setUserId(requestDto.getUserId());
        transaction.setAmount(requestDto.getAmount());
        transaction.setReceiptNo(requestDto.getReceiptNo());

        return transaction;
    }

    private TransactionDto createTransactionDto(Transaction transaction) {
        final TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setUserId(transaction.getUserId());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setReceiptNo(transaction.getReceiptNo());
        transactionDto.setCreatedAt(transaction.getCreatedAt());
        transactionDto.setUpdatedAt(transaction.getUpdatedAt());

        return transactionDto;
    }
}
