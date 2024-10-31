package com.ssmtest.entity;

import lombok.Data;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Data
public class Admin implements Serializable {

    private Integer id;
    private Integer account;
    private String password;
    private String awatar;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String verify;
    private String token;
    private String refreshToken;
    private Date buildTime;

}
