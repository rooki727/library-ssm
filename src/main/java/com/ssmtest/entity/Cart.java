package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Cart implements Serializable {
    private Integer cart_id;
    private Integer book_id;
    private Integer user_id;
}
