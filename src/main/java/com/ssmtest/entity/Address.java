package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Address implements Serializable {
    private Integer address_id;
    private Integer user_id;
    private String receiver;
    private String phone;
    private String bigAddress;
    private String fullAddress;
    private Integer isDefault;
    private Double freight;
}
