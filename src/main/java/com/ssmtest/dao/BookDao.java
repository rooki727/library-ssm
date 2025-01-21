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
    Category getCategorySaleList(String category);
    List<Book> getBooksGuessLike(@Param("offset") int offset, @Param("pageSize") int pageSize);
    int getTotalCount();

    String getGuessName();
    int selectAllCount();
    Book getCategoryFirstBook(String category);
    List<Book> getBookByCategory(@Param("category") String category,@Param("offset") int offset, @Param("pageSize") int pageSize);
    List<Book> getAllBookByCategory(@Param("category") String category);
    List<Book> fuzzyQueriesBookName(@Param("book_name") String book_name);

    Book getBookById(int book_id);
    String getGuessNameByCategory(String category);
    int getTotalPageByCategory(String category);
}
