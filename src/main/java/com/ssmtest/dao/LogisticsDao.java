package com.ssmtest.dao;

import com.ssmtest.entity.Logistics;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogisticsDao {
    List<Logistics> findAllLogistics(@Param("offset") int offset, @Param("pageSize") int pageSize);
    int getTotalPageLogistics();
    List<Logistics> findLogisticsById(@Param("order_id") int order_id);
    void deleteLogistics(int logistics_id);
    void  addLogistics(Logistics logistics);
}
