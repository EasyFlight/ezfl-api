package com.easyflight.flight.api;

import com.easyflight.flight.enums.ErrorCodes;
import com.easyflight.flight.exception.DuplicateException;
import com.easyflight.flight.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Victor.Ikoro on 8/8/2017.
 */

@ControllerAdvice(annotations = {RestController.class, Component.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiAdvice {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public Response handleNotFoundException(NotFoundException e) {
        logger.error(e.getMessage());
        Response response = new Response(e.getCode(), e.getMessage());
        return response;
    }

    @ExceptionHandler(DuplicateException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public Response handleDuplicateException(DuplicateException e) {
        logger.error(e.getMessage());
        Response response = new Response(e.getCode(), e.getMessage());
        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public Response handleException(Exception e) {
        logger.error(e.getMessage());
        Response response = new Response(ErrorCodes.INTERNAL_SERVER_ERROR.name(), e.getMessage());
        return response;
    }


    private class Response {
        private String code;
        private String message;

        Response(String code, String message) {

            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
