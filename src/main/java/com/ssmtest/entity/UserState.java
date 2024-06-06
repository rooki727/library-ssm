package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserState implements Serializable {
    private Integer todayAdd;
    private Integer monthAdd;
    private Integer userBuy;
    public UserState(int todayAdd,int monthAdd,int userBuy){
        this.todayAdd=todayAdd;
        this.monthAdd=monthAdd;
        this.userBuy=userBuy;
    }
}
