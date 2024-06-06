package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderSum implements Serializable {
    private Integer todayAdd;
    private Integer monthSaleCount;
    private Integer monthMoney;
    public OrderSum(int todayAdd,int monthSaleCount,int monthMoney){
        this.todayAdd=todayAdd;
        this.monthSaleCount=monthSaleCount;
        this.monthMoney=monthMoney;
    }
}
