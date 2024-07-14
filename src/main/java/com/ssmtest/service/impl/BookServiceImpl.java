package com.ssmtest.service.impl;

import com.ssmtest.dao.BookDao;
import com.ssmtest.entity.Book;
import com.ssmtest.entity.Category;
import com.ssmtest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("bookService")
public class BookServiceImpl implements BookService {

    @Autowired
    BookDao bookDao;
    @Override
    public List<Book> findAllBook() {
        System.out.println("Book业务层实现类--findAllBook");
        return bookDao.findAllBook();
    }

    @Override
    public Category findCategory(String category) {
        System.out.println("Book业务层实现类--findCategory");
        return bookDao.findCategory(category);
    }

    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public void deleteBook(int book_id) {
        bookDao.deleteBook(book_id);
    }

    @Override
    public int selectBookTodayAdd() {
        return bookDao.selectBookTodayAdd();
    }

    @Override
    public int selectBookMonthAdd() {
        return bookDao.selectBookMonthAdd();
    }

    @Override
    public int selectBookMonthOut() {
        return bookDao.selectBookMonthOut();
    }

    @Override
    public Category getMonthSaleList(String category) {
        return bookDao.getMonthSaleList(category);
    }
    @Override
    public List<Book> getGuessLikeBooks(int page, int pageSize) {
        // 在这里可以调用DAO层方法，访问数据库
        int offset = (page - 1) * pageSize;
        return bookDao.getBooksGuessLike(offset, pageSize);
    }

    @Override
    public int getTotalPages(int pageSize) {
        int totalCount = bookDao.getTotalCount();
        return (int) Math.ceil((double) totalCount / pageSize);
    }
}
