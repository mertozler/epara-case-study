package com.epara.exchange.controller;
import com.epara.exchange.dto.CreateTransactionRequest;
import com.epara.exchange.dto.TransactionDto;
import com.epara.exchange.exception.TransactionIsFailedException;
import com.epara.exchange.provider.ExchangeProvider;
import com.epara.exchange.model.Transaction;
import com.epara.exchange.service.TransactionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/exchanges")
public class ExchangeController {
    private final TransactionService transactionService;
    private final ExchangeProvider exchangeProvider;
    private final Gson gson ;
    Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    public ExchangeController(TransactionService transactionService, ExchangeProvider exchangeProvider) {
        this.transactionService = transactionService;
        this.exchangeProvider = exchangeProvider;
        this.gson = new GsonBuilder().create();
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@Valid @RequestBody CreateTransactionRequest request) throws IOException {
        logger.info("Create transaction request received for base currency: {} and target currencies: {}",
                request.getBaseCurrency(),
                request.getTargetCurrencies());

        String responseJson = exchangeProvider.getExchangeRatesByBaseAndTargetCurrencies(request.getBaseCurrency(),
                request.getTargetCurrencies());
        Transaction transaction = gson.fromJson(responseJson, Transaction.class);

        if(!transaction.isSuccess()){
            logger.error("Error while create transaction because transaction is not successful");
            throw new TransactionIsFailedException("Transaction could not be performed, " +
                    "please make sure you entered the currency information correctly.");
        }

        TransactionDto createdTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
