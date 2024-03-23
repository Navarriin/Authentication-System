package com.navarro.authenticationSystem.infra;

import com.navarro.authenticationSystem.exceptions.NotFoundException;
import com.navarro.authenticationSystem.exceptions.DefaultMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    private final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultMessage> DefaultErrorNotFoundHandler(NotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new DefaultMessage(NOT_FOUND, exception.getMessage()));
    }
}
