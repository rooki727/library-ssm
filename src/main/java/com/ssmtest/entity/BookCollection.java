package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class BookCollection implements Serializable {
    private Integer collection_id;
    private Integer book_id;
    private Integer user_id;
}
