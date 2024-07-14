package com.ssmtest.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GuessLikeResponse  implements Serializable {
    private List<Book> bookList;
    private int totalPages;
}
