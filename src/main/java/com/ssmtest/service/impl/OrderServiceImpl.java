package com.ssmtest.service.impl;

import com.ssmtest.dao.OrderDao;
import com.ssmtest.entity.Logistics;
import com.ssmtest.entity.Order;
import com.ssmtest.entity.OrderBook;
import com.ssmtest.entity.OrderDetail;
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

    @Override
    public void addOrderList(Order order) {
        orderDao.addOrderList(order);
    }

    @Override
    public int getOrderIdAfterInsert(Order order) {
        return orderDao.getOrderIdAfterInsert(order);
    }

    @Override
    public void addOrderDetail(OrderDetail orderDetail) {
        orderDao.addOrderDetail(orderDetail);
    }

    @Override
    public List<OrderDetail> getOrderDetailsWithBooks(int order_id) {
        return orderDao.getOrderDetailsWithBooks(order_id);
    }

    @Override
    public Order getOrderById(int order_id) {
        return orderDao.getOrderById(order_id);
    }

    @Override
    public void updateCancelReason(Order order) {
        orderDao.updateCancelReason(order);
    }

    @Override
    public void addNewLogistics(Logistics logistics) {
        orderDao.addNewLogistics(logistics);
    }

    @Override
    public List<Logistics> findLogisticsList(int order_id) {
        return orderDao.findLogisticsList(order_id);
    }

    @Override
    public int findCountStatus(int user_id,String order_status) {
        return orderDao.findCountStatus(user_id,order_status);
    }
}
