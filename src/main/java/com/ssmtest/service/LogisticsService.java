package com.ssmtest.service;

import com.ssmtest.entity.Logistics;

import java.util.List;

public interface LogisticsService {
    int getTotalPageLogistics();
    List<Logistics> findAllLogistics(int page, int pageSize);
    List<Logistics> findLogisticsById(int order_id);
    void deleteLogistics(int logistics_id);
    void  addLogistics(Logistics logistics);
}
