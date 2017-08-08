package com.easyflight.flight.exception;

/**
 * Created by Victor.Ikoro on 8/8/2017.
 */
public class NotFoundException extends ApplicationException {

    public NotFoundException(String code, String message) {
        super(code, message);
    }

    public NotFoundException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
