package com.ssmtest.service.impl;

import com.ssmtest.dao.SummaryDao;
import com.ssmtest.entity.Summary;
import com.ssmtest.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("summaryService")
public class SummaryServiceImpl implements SummaryService {

    @Autowired
    private SummaryDao summaryDao;
    @Override
    public List<Summary> findAllSummary() {
        System.out.println("Summary业务层实现类--findAllSummary");
        return summaryDao.findAllSummary();
    }

    @Override
    public void addSummary(Summary summary) {
        summaryDao.addSummary(summary);
    }

    @Override
    public void deleteSummary(int summary_id) {
        summaryDao.deleteSummary(summary_id);
    }
}
