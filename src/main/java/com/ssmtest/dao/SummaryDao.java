package com.ssmtest.dao;

import com.ssmtest.entity.Summary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryDao {

    List<Summary> findAllSummary();

    void addSummary(Summary summary);
 }
