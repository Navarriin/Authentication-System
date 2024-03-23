package com.navarro.authenticationSystem.exceptions;

import org.springframework.http.HttpStatus;

public record DefaultMessage(HttpStatus status, String message) {
}
