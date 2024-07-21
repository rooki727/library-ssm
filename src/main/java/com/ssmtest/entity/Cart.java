package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Data
public class Cart implements Serializable {
    private Integer cart_id;
    private Integer book_id;
    private Integer user_id;
    private Integer number;
    private Integer isSelected;
    private Date buildTime;
    public String getFormattedBuildTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(buildTime);
    }
}
