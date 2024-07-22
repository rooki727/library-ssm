package com.ssmtest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;


@Data
public class Cart implements Serializable {
    private Integer cart_id;
    private Integer book_id;
    private Integer user_id;
    private Integer number;
    private Integer isSelected;
//    标定前端传过来的数
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date buildTime;

}
