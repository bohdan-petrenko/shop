package com.kate.shop.exceptions.http;

import com.kate.shop.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class HttpException extends ApplicationException {

    private final HttpStatus httpStatus;


    public HttpException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpException(Throwable cause, HttpStatus httpStatus) {
        super(cause);
        this.httpStatus = httpStatus;
    }

    public HttpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus httpStatus) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
