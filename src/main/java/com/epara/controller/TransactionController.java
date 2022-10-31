package com.epara.controller;

import com.epara.dto.TransactionDto;
import com.epara.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable String id) {
        logger.info("Get transaction request received for: {}", id);
        TransactionDto transactionDto = transactionService.findTransactionById(id);
        return ResponseEntity.ok(transactionDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<TransactionDto>> getTransactionByDateRange(
            @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy")  LocalDate endDate) {
        logger.info("Get transaction request received with date range, start date: {} and end date: {}",
                startDate,
                endDate);

        List<TransactionDto> transactionDtoList = transactionService.findTransactionByDateRange(startDate,endDate);


        return ResponseEntity.ok(transactionDtoList);
    }
}