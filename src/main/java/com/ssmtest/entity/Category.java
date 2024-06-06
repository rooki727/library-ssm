package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Category implements Serializable {
    private String category;
    private Integer value;

}
