package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminRequest implements Serializable {
    private Admin admin;
    private int login_id;

}
