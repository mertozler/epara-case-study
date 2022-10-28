package com.epara.exchange.exception;

import com.epara.exchange.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ValidationErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Response> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        Response response = Response.validationException();
        response.addErrorMsgToResponse(ex.getFieldError().getDefaultMessage(), ex);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}
