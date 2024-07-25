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
    private  List<OrderDetail> orderDetailList;
//    orderlist表的
    private Order order;
}
