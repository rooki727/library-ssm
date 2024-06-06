package com.ssmtest.service;

import com.ssmtest.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrder();
    void updateOrder(Order order);
    void updateSummaryStatus(Order order);
    void deleteOrder(int order_id);

    int getTodayOrderAdd();
    int getMonthSaleCount();
    int getMonthMoney();
}
