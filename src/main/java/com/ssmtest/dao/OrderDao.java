package com.ssmtest.dao;


import com.ssmtest.entity.*;
import org.apache.ibatis.annotations.Param;
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
    void addOrderList(Order order);
    int getOrderIdAfterInsert(Order order);
    void addOrderDetail(OrderDetail orderDetail);
    List<OrderDetail> getOrderDetailsWithBooks(int order_id);
    Order getOrderById(int order_id);
    void updateCancelReason(Order order);
    void addNewLogistics(Logistics logistics);
    List<Logistics> findLogisticsList(int order_id);
    int findCountStatus(@Param("user_id") int user_id,@Param("order_status") String order_status);
    int findCountAllByUser(@Param("user_id") int user_id);
//    进行订单列表
    List<Order> getOrderBookListByStatus(@Param("order_status") String order_status, @Param("user_id") int user_id,@Param("offset") int offset, @Param("pageSize") int pageSize);
}
