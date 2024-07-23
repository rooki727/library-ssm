package com.ssmtest.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssmtest.entity.*;
import com.ssmtest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/order")
public class OrderController{
    @Autowired
    private OrderService orderService;

    @GetMapping("/findAllOrder")
    @ResponseBody
    public List<Order> findAllOrder(){
        System.out.println("表现层--查询所有订单");
        try {
            //调用service的方法
            List<Order> orderList = orderService.findAllOrder();
            return orderList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @PostMapping("/updateOrder")
    @ResponseBody
    public String updateOrder(@RequestBody Order order){
        try {
            orderService.updateOrder(order);
            return "更新订单状态成功！";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/updateSummaryStatus")
    @ResponseBody
    public String updateSummaryStatus(@RequestBody Order order){
        try {
            orderService.updateSummaryStatus(order);
            return "更新汇总状态成功！";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/deleteOrder")
    @ResponseBody
    public String deleteOrder(@RequestBody Map<String, Object> requestBody){
        int order_id = (int) requestBody.get("order_id");
        try {
            orderService.deleteOrder(order_id);
            return "删除订单成功！";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/getOrderSum")
    @ResponseBody
    public OrderSum getOrderSum(){
        try {
            int todayAdd=orderService.getTodayOrderAdd();
            int monthSaleCount=orderService.getMonthSaleCount();
            int monthMoney=orderService.getMonthMoney();
            return new OrderSum(todayAdd,monthSaleCount,monthMoney);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @PostMapping("/addOrderList")
    @ResponseBody
    public ApiResponse<Integer> addOrderList(@RequestBody Order order){
        ApiResponse<Integer> response=new ApiResponse<>();
        try {
           orderService.addOrderList(order);
           int order_id=orderService.getOrderIdAfterInsert(order);
            response.setCode("1");
            response.setMsg("添加订单成功");
            response.setResult(order_id);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @PostMapping("/addOrderDetail")
    @ResponseBody
    public ApiResponse<Boolean> addOrderDetail(@RequestBody OrderDetail orderDetail){
        ApiResponse<Boolean> response=new ApiResponse<>();
        try {
            orderService.addOrderDetail(orderDetail);
            response.setCode("1");
            response.setMsg("添加订单成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @PostMapping("/getOrderDetailsWithBooks")
    @ResponseBody
    public ApiResponse<List<OrderBook>> getOrderDetailsWithBooks(@RequestBody Map<String, Object> requestBody){
        ApiResponse<List<OrderBook>> response=new ApiResponse<>();
        int order_id = (int) requestBody.get("order_id");
        try {
          List<OrderBook> orderBookList=  orderService.getOrderDetailsWithBooks(order_id);
            response.setCode("1");
            response.setMsg("查询订单");
            response.setResult(orderBookList);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
