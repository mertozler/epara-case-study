package com.epara.exception;

import com.epara.dto.response.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request) {
        Response response = Response.validationException();
        response.addErrorMsgToResponse(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

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
