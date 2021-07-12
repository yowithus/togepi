package com.example.togepi.controller;

import com.example.togepi.constant.ApiConstant;
import com.example.togepi.dto.CreateTransactionRequestDto;
import com.example.togepi.dto.SearchTransactionRequestDto;
import com.example.togepi.dto.TransactionDto;
import com.example.togepi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.example.togepi.constant.ApiConstant.PARAM_RECEIPT_NO;
import static com.example.togepi.constant.ApiConstant.PARAM_USER_ID;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(ApiConstant.CREATE_TRANSACTION)
    public void insert(@RequestBody CreateTransactionRequestDto createTransactionRequestDto) {
        transactionService.create(createTransactionRequestDto);
    }

    @GetMapping(ApiConstant.FIND_TRANSACTION_BY_ID)
    public TransactionDto findById(
            @PathVariable UUID id) {
        return transactionService.findById(id);
    }

    @GetMapping(ApiConstant.SEARCH_TRANSACTIONS)
    public List<TransactionDto> search(
            @RequestParam(value = PARAM_USER_ID) Long userId,
            @RequestParam(value = PARAM_RECEIPT_NO) String receiptNo) {
        final SearchTransactionRequestDto requestDto = new SearchTransactionRequestDto();
        requestDto.setUserId(userId);
        requestDto.setReceiptNo(receiptNo);

        return transactionService.search(requestDto);
    }
}
