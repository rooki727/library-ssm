package com.ssmtest.service;

import com.ssmtest.entity.Order;
import com.ssmtest.entity.OrderBook;
import com.ssmtest.entity.OrderDetail;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrder();
    void updateOrder(Order order);
    void updateSummaryStatus(Order order);
    void deleteOrder(int order_id);

    int getTodayOrderAdd();
    int getMonthSaleCount();
    int getMonthMoney();
    void addOrderList(Order order);
    int getOrderIdAfterInsert(Order order);
    void addOrderDetail(OrderDetail orderDetail);
    List<OrderBook> getOrderDetailsWithBooks(int order_id);
}
