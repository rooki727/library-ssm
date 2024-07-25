package com.ssmtest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class Logistics implements Serializable {
    private Integer logistics_id;
    private Integer order_id;
    private String text;
    private String company_name;
    private String logistics_number;
    private String company_tel;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",locale = "zh", timezone = "GMT+8")
    private Date time;
    // 无参构造方法
    public Logistics() {
    }

    // 全参构造方法
    public Logistics( Integer order_id, String text, String company_name,
                      String logistics_number, String company_tel, Date time) {
        this.order_id = order_id;
        this.text = text;
        this.company_name = company_name;
        this.logistics_number = logistics_number;
        this.company_tel = company_tel;
        this.time=time;
    }
}
