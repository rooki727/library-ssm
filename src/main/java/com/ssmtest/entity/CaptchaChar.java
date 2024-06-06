package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CaptchaChar implements Serializable {
    private char value;
    private int position;
    private int rotation;
    public CaptchaChar(char value, int position, int rotation) {
        this.value = value;
        this.position = position;
        this.rotation = rotation;
    }
}
