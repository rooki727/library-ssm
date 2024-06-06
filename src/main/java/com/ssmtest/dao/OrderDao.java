package com.ssmtest.dao;


import com.ssmtest.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {
    List<Order> findAllOrder();
    void updateOrder(Order order);

    void updateSummaryStatus(Order order);
    void deleteOrder(int order_id);

    int getTodayOrderAdd();
    int getMonthSaleCount();
    int getMonthMoney();


}
