package com.crud_repeat_nocopy_0828.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialException extends ApplicationException {

    public InvalidCredentialException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}