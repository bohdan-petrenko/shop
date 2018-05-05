package com.kate.shop.configuration;

import com.kate.shop.exceptions.http.HttpException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(HttpException.class)
    public String httpException(HttpException e, HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(e.getHttpStatus().value());
        return e.getMessage();
    }
}
