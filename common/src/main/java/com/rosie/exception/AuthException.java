package com.rosie.exception;

import java.io.Serial;

public class AuthException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -6397561948112312174L;

    public AuthException(String message) {
        super(message);
    }
}
