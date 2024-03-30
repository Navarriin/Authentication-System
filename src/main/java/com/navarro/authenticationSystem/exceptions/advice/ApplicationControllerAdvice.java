package com.navarro.authenticationSystem.exceptions.advice;

import com.navarro.authenticationSystem.exceptions.ExistingUserException;
import com.navarro.authenticationSystem.exceptions.NotFoundException;
import com.navarro.authenticationSystem.exceptions.message.DefaultMessage;
import com.navarro.authenticationSystem.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    private final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private final HttpStatus FORBIDDEN = HttpStatus.FORBIDDEN;
    private final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultMessage> DefaultErrorNotFoundHandler(NotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new DefaultMessage(NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<DefaultMessage> DefaultErrorExistingUserHandler(ExistingUserException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new DefaultMessage(BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<DefaultMessage> UnauthorizedExceptionHandler(UnauthorizedException exception) {
        return ResponseEntity.status(FORBIDDEN)
                .body(new DefaultMessage(FORBIDDEN, exception.getMessage()));
    }
}
