package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetail implements Serializable {
    private Integer order_detail_id;
    private Integer order_id;
    private Integer book_id;
    private Integer detail_number;
}
