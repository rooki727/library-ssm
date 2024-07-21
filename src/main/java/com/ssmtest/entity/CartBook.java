package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
@Data
public class CartBook implements Serializable {
    private Integer cart_id;
    private Integer book_id;
    private Integer user_id;
    private Integer number;
    private Integer isSelected;
    private Date buildTime;
//书本部分拿的
    private String book_name;
    private String author;
    private String category;
    private String press;
    private Double price;
    private Integer stock;
    private Integer sale_number;
    private String main_picture;
    private String introduce;
    public String getFormattedBuildTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(buildTime);
    }
}
