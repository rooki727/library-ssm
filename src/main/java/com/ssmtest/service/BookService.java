package com.ssmtest.service;

import com.ssmtest.entity.Book;
import com.ssmtest.entity.Category;

import java.util.List;

public interface BookService {
    List<Book> findAllBook();
    Category findCategory(String category);
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(int book_id);
    int selectBookTodayAdd();
    int selectBookMonthAdd();
    int selectBookMonthOut();

    Category getMonthSaleList(String category);
}