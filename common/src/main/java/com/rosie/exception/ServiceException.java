package com.rosie.exception;


import java.io.Serial;

public class ServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -378067821581777518L;

    public ServiceException(String message) {
        super(message);
    }

}
