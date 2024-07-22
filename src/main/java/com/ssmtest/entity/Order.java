package com.ssmtest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {
    private Integer order_id;
    private Integer book_id;
    private Integer user_id;
    private Double price;
    private Integer count;
    private String order_status;
    private String summary_status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date buildTime;
}
