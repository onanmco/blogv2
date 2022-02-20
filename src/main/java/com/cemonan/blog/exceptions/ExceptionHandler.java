package com.cemonan.blog.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import redis.clients.jedis.exceptions.JedisException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@RestController
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        ExceptionResponse response = new ExceptionResponse();

        response.setTimestamp(new Date());
        response.setMessage("Invalid parameters in request body.");
        if (details.size() > 0) {
            response.setDetails(details);
        }

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public final ResponseEntity<Object> handleJedisException(JedisException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();

        response.setTimestamp(new Date());
        response.setMessage("Internal server error.");

        System.out.println("An error occurred while establishing a new connection with Redis.");
        ex.printStackTrace();

        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public final ResponseEntity<Object> handleTokenNotProvidedException(TokenNotProvidedException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();

        response.setTimestamp(new Date());
        response.setMessage("Unauthorized");

        return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public final ResponseEntity<Object> handleTokenNotFoundException(TokenNotFoundException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();

        response.setTimestamp(new Date());
        response.setMessage("Unauthorized");

        return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public final ResponseEntity<Object> handleCannotResolveApplicationPropertiesPathException(CannotResolveApplicationPropertiesPathException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();

        ex.printStackTrace();

        response.setTimestamp(new Date());
        response.setMessage("Internal server error");

        return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
    }
}
