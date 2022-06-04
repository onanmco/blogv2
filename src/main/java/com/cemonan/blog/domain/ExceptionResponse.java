package com.cemonan.blog.domain;

import lombok.Data;

import java.util.List;

@Data
public class ExceptionResponse {

    private Long timestamp;
    private int status;
    private String error;
    private String path;
    private List<String> details;
}
