package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResponse<T> implements Serializable {
    private String code;
    private String msg;
    private T result;

    // Getters and setters
}

