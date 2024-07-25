package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Data
public class OrderDetail implements Serializable {
    private Integer order_detail_id;
    private Integer order_id;
    private Integer book_id;
    private Integer detail_number;
//book
    private String book_name;
    private Double price;
    private Integer stock;
    private String main_picture;

}
