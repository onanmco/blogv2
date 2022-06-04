package com.cemonan.blog.exception;

import lombok.Data;

import java.util.List;

@Data
public class ValidationException extends RuntimeException {

    private List<String> details;

    public ValidationException(List<String> details) {
        this.details = details;
    }
}
