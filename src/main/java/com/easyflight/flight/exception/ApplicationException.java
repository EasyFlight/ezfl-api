package com.easyflight.flight.exception;

/**
 * Created by Victor.Ikoro on 8/8/2017.
 */
public class ApplicationException extends RuntimeException {
    protected String code;

    public ApplicationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ApplicationException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
