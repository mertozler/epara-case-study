package com.epara.epara.controller;

import com.epara.epara.dto.CreateTransactionRequest;
import com.epara.epara.dto.TransactionDto;
import com.epara.epara.dto.response.Response;
import com.epara.epara.exception.TransactionIsFailedException;
import com.epara.epara.model.Transaction;
import com.epara.epara.provider.ExchangeProvider;
import com.epara.epara.service.TransactionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/v1/api/exchanges")
public class ExchangeController {
    private final TransactionService transactionService;
    private final ExchangeProvider exchangeProvider;
    private final Gson gson;
    Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    public ExchangeController(TransactionService transactionService, ExchangeProvider exchangeProvider) {
        this.transactionService = transactionService;
        this.exchangeProvider = exchangeProvider;
        this.gson = new GsonBuilder().create();
    }

    @PostMapping
    public ResponseEntity createTransaction(@Valid @RequestBody CreateTransactionRequest request) throws IOException {
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

        return new ResponseEntity(Response.created().setPayload(createdTransaction), HttpStatus.CREATED);
    }

}
