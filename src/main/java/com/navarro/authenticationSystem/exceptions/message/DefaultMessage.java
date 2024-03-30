package com.navarro.authenticationSystem.exceptions.message;

import org.springframework.http.HttpStatus;

public record DefaultMessage(HttpStatus status, String message) {
}
