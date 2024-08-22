package com.ssmtest.service.impl;

import com.ssmtest.dao.LogisticsDao;
import com.ssmtest.entity.Logistics;
import com.ssmtest.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("logisticsService")
public class LogisticsServiceImpl implements LogisticsService {
@Autowired
private LogisticsDao logisticsDao;

    @Override
    public int getTotalPageLogistics() {
        return logisticsDao.getTotalPageLogistics();
    }

    @Override
    public List<Logistics> findAllLogistics(int page, int pageSize) {
        // 在这里可以调用DAO层方法，访问数据库
        int offset = (page - 1) * pageSize;
        return logisticsDao.findAllLogistics(offset, pageSize);
    }

    @Override
    public List<Logistics> findLogisticsById(int order_id) {
        return logisticsDao.findLogisticsById(order_id);
    }

    @Override
    public void deleteLogistics(int logistics_id) {
        logisticsDao.deleteLogistics(logistics_id);
    }

    @Override
    public void addLogistics(Logistics logistics) {
        logisticsDao.addLogistics(logistics);
    }
}
