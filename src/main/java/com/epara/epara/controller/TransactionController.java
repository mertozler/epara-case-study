package com.epara.epara.controller;

import com.epara.epara.dto.TransactionDto;
import com.epara.epara.dto.response.Response;
import com.epara.epara.exception.TransactionListIsEmptyException;
import com.epara.epara.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Object>> getTransactionById(@PathVariable String id) {
        logger.info("Get transaction request received for: {}", id);
        TransactionDto transactionDto = transactionService.findTransactionById(id);
        return new ResponseEntity<>(Response.ok().setPayload(transactionDto), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Response<Object>> getTransactionByDateRange(
            @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern="dd/MM/yyyy")  LocalDate endDate) {
        logger.info("Get transaction request received with date range, start date: {} and end date: {}",
                startDate,
                endDate);

        List<TransactionDto> transactionDtoList = transactionService.findTransactionByDateRange(startDate,endDate);

        if(transactionDtoList.isEmpty()){
            throw new TransactionListIsEmptyException("No transaction data can be found in this date range. " +
                    "Please check the date range you entered.");
        }

        return new ResponseEntity<>(Response.ok().setPayload(transactionDtoList), HttpStatus.OK);
    }
}