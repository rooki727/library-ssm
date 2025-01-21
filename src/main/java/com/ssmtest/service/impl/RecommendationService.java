package com.ssmtest.service.impl;

import com.ssmtest.dao.BookDao;
import com.ssmtest.dao.UserBookDao;
import com.ssmtest.entity.Book;
import com.ssmtest.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("userBookService")
public class RecommendationService {

    @Autowired
    private UserBookDao userBookDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private IUserService userService;

    // 根据用户id获取收藏书籍列表
    private List<Integer> getBooksByUserId(int userId) {
        return userBookDao.getBooksByUserId(userId);
    }

    // 根据书籍ID获取书籍信息
    private Book getBookById(int bookId) {
        return bookDao.getBookById(bookId);
    }

    // 根据书籍ID获取书籍分类
    private String getBookCategory(int bookId) {
        Book book = getBookById(bookId);
        return book != null ? book.getCategory() : null;
    }

    // 获取指定分类下的所有书籍
    private List<Book> getBooksByCategory(String category) {
        return bookDao.getAllBookByCategory(category);
    }

    public List<Book> recommendBooks(int userId, int page, int pageSize) {
        // 获取用户已收藏的书籍ID列表
        List<Integer> bookIds = getBooksByUserId(userId);
        if (bookIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 获取每本书籍的分类
        Map<String, Integer> categoryCount = new HashMap<>();
        for (int bookId : bookIds) {
            String category = getBookCategory(bookId);
            if (category != null) {
                categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
            }
        }

        // 按分类的收藏次数降序排序
        List<Map.Entry<String, Integer>> sortedCategories = categoryCount.entrySet().stream()
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()))
                .collect(Collectors.toList());

        List<Book> recommendedBooks = new ArrayList<>();
        int recommendedCount = 0;  // 已推荐的书籍数量

        // 遍历按收藏次数排序的分类，推荐该分类的书籍
        for (Map.Entry<String, Integer> entry : sortedCategories) {
            String category = entry.getKey();
            List<Book> booksInCategory = getBooksByCategory(category);

            // 遍历当前分类下的书籍，进行分页推荐
            for (Book book : booksInCategory) {
                // 如果推荐数量已达到目标数量，则跳出
                if (recommendedCount >= page * pageSize) {
                    break;
                }

                // 在分页范围内，加入推荐书籍
                if (recommendedCount >= (page - 1) * pageSize && recommendedCount < page * pageSize) {
                    recommendedBooks.add(book);
                }

                // 如果是用户已收藏的书籍，仍然被推荐，但不重复
                recommendedCount++;
            }

            // 如果已推荐的书籍数量达到所需页面的数量，跳出循环
            if (recommendedCount >= page * pageSize) {
                break;
            }
        }

        return recommendedBooks;
    }
}

