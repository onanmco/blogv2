package com.cemonan.blog.exception;

import com.cemonan.blog.domain.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResponseEntityExceptionHandlerImpl extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();

        response.setTimestamp(Instant.now().getEpochSecond());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setError(ex.getMessage());
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public final ResponseEntity<Object> handleEmailAlreadyTakenException(EmailAlreadyTakenException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();

        response.setTimestamp(Instant.now().getEpochSecond());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError(ex.getMessage());
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();

        response.setTimestamp(Instant.now().getEpochSecond());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("Validation error.");
        response.setDetails(ex.getDetails());
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public final ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();

        response.setTimestamp(Instant.now().getEpochSecond());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();

        response.setTimestamp(Instant.now().getEpochSecond());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();

        response.setTimestamp(Instant.now().getEpochSecond());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setError("Invalid credentials.");
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();

        response.setTimestamp(Instant.now().getEpochSecond());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());

        ExceptionResponse response = new ExceptionResponse();

        response.setTimestamp(Instant.now().getEpochSecond());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError("Validation error.");
        response.setDetails(details);
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}
