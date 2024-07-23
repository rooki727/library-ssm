package com.ssmtest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OrderBook implements Serializable {
//    orderdetail表
    private Integer order_detail_id;
    private Integer book_id;
    private Integer detail_number;
//    orderlist表的
private Integer order_id;
    private Integer user_id;
    private Double order_money;
    private Integer number;
    private String order_status;
    private String summary_status;
    private String remark;
    private String delivery;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date buildTime;
//    book的
    private String book_name;
    private Double price;
    private String main_picture;
}
