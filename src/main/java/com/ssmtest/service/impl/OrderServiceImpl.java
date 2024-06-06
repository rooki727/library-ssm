package com.ssmtest.service.impl;

import com.ssmtest.dao.OrderDao;
import com.ssmtest.entity.Order;
import com.ssmtest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;;
    @Override
    public List<Order> findAllOrder() {
        System.out.println("Order业务层实现类--findAllOrder");
        return orderDao.findAllOrder();
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    @Override
    public void updateSummaryStatus(Order order) {
        orderDao.updateSummaryStatus(order);
    }

    @Override
    public void deleteOrder(int order_id) {
        orderDao.deleteOrder(order_id);
    }

    @Override
    public int getTodayOrderAdd() {
        return orderDao.getTodayOrderAdd();
    }

    @Override
    public int getMonthSaleCount() {
        return orderDao.getMonthSaleCount();
    }

    @Override
    public int getMonthMoney() {
        return orderDao.getMonthMoney();
    }
}
