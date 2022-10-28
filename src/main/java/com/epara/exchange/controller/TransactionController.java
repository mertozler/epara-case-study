package com.epara.exchange.controller;

import com.epara.exchange.dto.TransactionDto;
import com.epara.exchange.exception.TransactionListIsEmptyException;
import com.epara.exchange.provider.ExchangeProvider;
import com.epara.exchange.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final ExchangeProvider exchangeProvider;
    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController(TransactionService transactionService, ExchangeProvider exchangeProvider) {
        this.transactionService = transactionService;
        this.exchangeProvider = exchangeProvider;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable String id) {
        logger.info("Get transaction request received for: {}", id);
        return ResponseEntity.ok(transactionService.findTransactionById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<TransactionDto>> getTransactionByDateRange(@RequestParam LocalDate startDate,
                                                                          @RequestParam  LocalDate endDate) {

        logger.info("Get transaction request received with date range, start date: {} and end date: {}",
                startDate,
                endDate);

        List<TransactionDto> transactionDtoList = transactionService.findTransactionByDateRange(startDate,endDate);

        if(transactionDtoList.isEmpty()){
            throw new TransactionListIsEmptyException("No transaction data can be found in this date range. " +
                    "Please check the date range you entered.");
        }
        return ResponseEntity.ok(transactionDtoList);
    }
}