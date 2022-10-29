package com.epara.epara.exception;

import com.epara.epara.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TransactionListIsEmptyException.class)
    public final ResponseEntity handleTransactionListIsEmptyException(Exception ex) {
        Response response = Response.notFound();
        response.addErrorMsgToResponse("TransactionListIsEmptyException", ex);
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TransactionNotFoundException.class)
    public final ResponseEntity handleTransactionNotFoundException(Exception ex) {
        Response response = Response.notFound();
        response.addErrorMsgToResponse("TransactionNotFoundException", ex);
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CurrencyCodeIsNotValidException.class)
    public final ResponseEntity handleCurrencyCodeIsNotValidException(Exception ex) {
        Response response = Response.badRequest();
        response.addErrorMsgToResponse("CurrencyCodeIsNotValidException", ex);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}
