package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class OrderStatus implements Serializable {
    private String status;
    private Integer count;
    public OrderStatus( String status,Integer count){
        this.status=status;
        this.count=count;
    }
}
