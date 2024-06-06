package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookSum implements Serializable {
    private Integer todayAdd;
    private Integer monthAdd;
    private Integer monthOut;
    public BookSum(int todayAdd,int monthAdd,int monthOut){
        this.todayAdd=todayAdd;
        this.monthAdd=monthAdd;
        this.monthOut=monthOut;
    }
}
