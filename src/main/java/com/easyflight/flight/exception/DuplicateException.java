package com.easyflight.flight.exception;

/**
 * Created by Victor.Ikoro on 8/8/2017.
 */
public class DuplicateException extends ApplicationException {
    public DuplicateException(String code, String message) {
        super(code, message);
    }

    public DuplicateException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
