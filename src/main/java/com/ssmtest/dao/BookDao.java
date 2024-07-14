package com.ssmtest.dao;


import com.ssmtest.entity.Book;
import com.ssmtest.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookDao {
    List<Book> findAllBook();

    Category findCategory(String category);

    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(int book_id);

    int selectBookTodayAdd();
    int selectBookMonthAdd();
    int selectBookMonthOut();
    Category getMonthSaleList(String category);
    List<Book> getBooksGuessLike(@Param("offset") int offset, @Param("pageSize") int pageSize);
    int getTotalCount();
}
