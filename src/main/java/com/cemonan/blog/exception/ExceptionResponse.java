package com.cemonan.blog.exception;

import java.util.Date;
import java.util.List;

public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private List<String> details;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}