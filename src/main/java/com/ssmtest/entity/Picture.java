package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Picture implements Serializable {
    private String url;
    // 无参构造方法
    public Picture() {
    }

    // 带参构造方法
    public Picture(String url) {
        this.url = url;
    }
}
