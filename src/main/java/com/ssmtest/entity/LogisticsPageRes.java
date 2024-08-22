package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LogisticsPageRes implements Serializable {
    private List<Logistics> logisticsList;
    private int totalPage;
}
