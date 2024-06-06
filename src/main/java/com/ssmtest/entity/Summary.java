package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Data
public class Summary implements Serializable {
    private Integer summary_id;
    private Integer user_id;
    private Double totalMoney;
    private Integer totalCount;
    private Date buildTime;
    public String getFormattedBuildTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(buildTime);
    }
}
