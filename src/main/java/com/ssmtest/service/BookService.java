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

    Category getCategorySaleList(String category);
    List<Book> getGuessLikeBooks(int page, int pageSize);
    int getTotalPages(int pageSize);
    String getGuessName();
    int selectAllCount();
    Book getCategoryFirstBook(String category);
    List<Book> getBookByCategory(String category,int page,int pageSize);
    List<Book> fuzzyQueriesBookName(String book_name);
    Book getBookById(int book_id);
}
