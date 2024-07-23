package com.ssmtest.service;

import com.ssmtest.entity.Summary;

import java.util.List;

public interface SummaryService {
    List<Summary> findAllSummary();

    void addSummary(Summary summary);
    void deleteSummary(int summary_id);
}
