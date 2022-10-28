package com.epara.exchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionListIsEmptyException extends RuntimeException {
    public TransactionListIsEmptyException(String message){
        super(message);
    }
}
