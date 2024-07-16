package com.ssmtest.entity;


import lombok.Data;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Data
public class Book implements Serializable {
    private Integer book_id;
    private String book_name;
    private String author;
    private String category;
    private Double price;
    private Integer stock;
    private Integer sale_number;
    private Date buildTime;
    private String picture;
    private String main_picture;
    private String introduce;
    private List<Picture> pictureList;
    public String getFormattedBuildTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(buildTime);
    }

    }


