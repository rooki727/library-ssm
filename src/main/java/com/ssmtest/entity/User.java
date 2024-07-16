package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Data
public class User implements Serializable {
    private Integer user_id;
    private Integer account;
    private String password;
    private String name;
    private String gender;
    private String phone;
    private String token;
    private String email;
    private String awatar;
    private String verify;
    private Date buildTime;
    private String address;
    private Date birthday;

    public String getFormattedBuildTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(buildTime);
    }
    public String getFormattedBirthday() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(birthday.toString().length()>0){
            return formatter.format(birthday);
        }
      return null;
    }
}
