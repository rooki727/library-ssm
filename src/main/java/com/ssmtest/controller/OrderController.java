package com.ssmtest.controller;

import com.ssmtest.entity.BookSum;
import com.ssmtest.entity.Order;
import com.ssmtest.entity.OrderSum;
import com.ssmtest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        //调用service的方法
        List<Order> orderList = orderService.findAllOrder();
        return orderList;
    }


    @PostMapping("/updateOrder")
    @ResponseBody
    public String updateOrder(@RequestBody Order order){
        orderService.updateOrder(order);
        return "更新订单状态成功！";
    }

    @PostMapping("/updateSummaryStatus")
    @ResponseBody
    public String updateSummaryStatus(@RequestBody Order order){
        orderService.updateSummaryStatus(order);
        return "更新汇总状态成功！";
    }

    @PostMapping("/deleteOrder")
    @ResponseBody
    public String deleteOrder(@RequestBody Map<String, Object> requestBody){
        int order_id = (int) requestBody.get("order_id");
        orderService.deleteOrder(order_id);
        return "删除订单成功！";
    }

    @GetMapping("/getOrderSum")
    @ResponseBody
    public OrderSum getOrderSum(){
        int todayAdd=orderService.getTodayOrderAdd();
        int monthSaleCount=orderService.getMonthSaleCount();
        int monthMoney=orderService.getMonthMoney();
        return new OrderSum(todayAdd,monthSaleCount,monthMoney);
    }
}
