package com.epara.epara.exception;

import com.epara.epara.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ValidationErrorHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Response> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        Response response = Response.validationException();
        response.addErrorMsgToResponse(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
