package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

@Data
public class Book implements Serializable {
    private Integer book_id;
    private String book_name;
    private String author;
    private String category;
    private Double price;
    private String status;
    private Date buildTime;

    public String getFormattedBuildTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(buildTime);
    }
}
