package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class User implements Serializable {
    private Integer user_id;
    private Integer account;
    private String password;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String verify;
    private Date buildTime;
}
