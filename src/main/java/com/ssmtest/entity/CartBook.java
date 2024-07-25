package com.ssmtest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;


@Data
public class CartBook implements Serializable {
    private Integer cart_id;
    private Integer book_id;
    private Integer user_id;
    private Integer detail_number;
    private Integer isSelected;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
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
}
