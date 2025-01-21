package com.ssmtest.dao;

import java.util.List;
import com.ssmtest.entity.Book;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookDao {
        List<Integer> getBooksByUserId(int userId); // 获取用户收藏的书籍列表
        List<Integer> getUsersByBookId(int bookId); // 获取收藏某本书的用户列表
        List<Book> getBooksByCategory(String category); // 获取指定分类的所有书籍
}
