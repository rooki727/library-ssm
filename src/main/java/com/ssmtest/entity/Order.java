package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Data
public class Order implements Serializable {
    private Integer order_id;
    private Integer book_id;
    private Integer user_id;
    private Double price;
    private String order_status;
    private String summary_status;
    private Date buildTime;
    public String getFormattedBuildTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(buildTime);
    }
}
