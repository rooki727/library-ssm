package com.ssmtest.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssmtest.entity.*;
import com.ssmtest.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
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
            return "success update！";
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
    public ApiResponse<Boolean> deleteOrder(@RequestBody Map<String, Object> requestBody){
        int order_id = (int) requestBody.get("order_id");
        ApiResponse<Boolean> response=new ApiResponse<>();
        try {
            orderService.deleteOrder(order_id);
            response.setCode("1");
            response.setMsg("删除订单成功");
            response.setResult(true);
            return response;
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
    public ApiResponse<OrderBook> getOrderDetailsWithBooks(@RequestBody Map<String, Object> requestBody){
        ApiResponse<OrderBook> response=new ApiResponse<>();
        int order_id = (int) requestBody.get("order_id");
        try {
            OrderBook orderBook=new OrderBook();
            List<OrderDetail> orderBookList=  orderService.getOrderDetailsWithBooks(order_id);
            Order order=orderService.getOrderById(order_id);
            orderBook.setOrder(order);
            orderBook.setOrderDetailList(orderBookList);
            response.setCode("1");
            response.setMsg("查询订单");
            response.setResult(orderBook);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/updateCancelReason")
    @ResponseBody
    public ApiResponse<Boolean> updateCancelReason(@RequestBody Order order){
        ApiResponse<Boolean> response=new ApiResponse<>();
        try {
            orderService.updateOrder(order);
            orderService.updateCancelReason(order);
            response.setCode("1");
            response.setMsg("取消订单成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @PostMapping("/updateDaiShouHuo")
    @ResponseBody
    public ApiResponse<Boolean> updateDaiShouHuo(@RequestBody Order order){
        ApiResponse<Boolean> response=new ApiResponse<>();
        try {
            orderService.updateOrder(order);
            Date date=new Date();
            Logistics logistics=new Logistics(order.getOrder_id(),"您的包裹已经在广州番薯区完成收件","rooki","12345678","13571231412",date);
            System.out.println(logistics);
            orderService.addNewLogistics(logistics);
            response.setCode("1");
            response.setMsg("订单模拟发货成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/updateDaiPingJia")
    @ResponseBody
    public ApiResponse<Boolean> updateDaiPingJia(@RequestBody Order order){
        ApiResponse<Boolean> response=new ApiResponse<>();
        try {
            orderService.updateOrder(order);
            Date date=new Date();
            Logistics logistics=new Logistics(order.getOrder_id(),"您的包裹已经在信宜番薯区签收成功","rooki","12345678","13571231412",date);
            System.out.println(logistics);
            orderService.addNewLogistics(logistics);
            response.setCode("1");
            response.setMsg("订单收货成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/findLogisticsList")
    @ResponseBody
    public ApiResponse<List<Logistics>> findLogisticsList(@RequestBody Map<String, Object> requestBody){
        ApiResponse<List<Logistics>> response=new ApiResponse<>();
        int order_id = (int) requestBody.get("order_id");
        try {
            List<Logistics> logisticsList=  orderService.findLogisticsList(order_id);
            response.setCode("1");
            response.setMsg("订单收货成功");
            response.setResult(logisticsList);
            return response;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/findCountStatus")
    @ResponseBody
    public ApiResponse<List<OrderStatus>> findCountStatus(@RequestBody Map<String, Object> requestBody){
        ApiResponse<List<OrderStatus>> response=new ApiResponse<>();
        int user_id = (int) requestBody.get("user_id");
        System.out.println(user_id);
        try {
            int daiFuKuan=orderService.findCountStatus(user_id,"待付款");
            OrderStatus orderStatus=new OrderStatus("待付款",daiFuKuan);
            int daiFaHuo=orderService.findCountStatus(user_id,"待发货");
            OrderStatus orderStatus2=new OrderStatus("待发货",daiFaHuo);
            int daiShouHuo=orderService.findCountStatus(user_id,"待收货");
            OrderStatus orderStatus3=new OrderStatus("待收货",daiShouHuo);
            int daiPingJia=orderService.findCountStatus(user_id,"待评价");
            OrderStatus orderStatus4=new OrderStatus("待评价",daiPingJia);
            int yiTuiKuan=orderService.findCountStatus(user_id,"已退款");
            OrderStatus orderStatus5=new OrderStatus("退款/售后",yiTuiKuan);
            List<OrderStatus> orderStatusList=new ArrayList<>();
            orderStatusList.add(orderStatus);
            orderStatusList.add(orderStatus2);
            orderStatusList.add(orderStatus3);
            orderStatusList.add(orderStatus4);
            orderStatusList.add(orderStatus5);

            response.setCode("1");
            response.setMsg("查询订单状态数量成功");
            response.setResult(orderStatusList);
            return response;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
