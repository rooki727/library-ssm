package com.ssmtest.controller;

import com.ssmtest.entity.Book;
import com.ssmtest.entity.BookSum;
import com.ssmtest.entity.Category;
import com.ssmtest.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;


    @GetMapping("/findAllBook")
    @ResponseBody
    public List<Book> findAllBook(){
        System.out.println("表现层--查询所有书本");
        //调用service的方法
        List<Book> bookList = bookService.findAllBook();
        return bookList;
    }

    @GetMapping("/findCategory")
    @ResponseBody
    public List<Category> findCategory(){
        System.out.println("表现层--查询所有分类");
        // 调用service的方法
        List<Category> categoryList = new ArrayList<>(); // 初始化categoryList

        // 调用service的方法并添加到categoryList中
        categoryList.add(bookService.findCategory("文学类"));
        categoryList.add(bookService.findCategory("科学类"));
        categoryList.add(bookService.findCategory("历史类"));
        categoryList.add(bookService.findCategory("科普类"));
        categoryList.add(bookService.findCategory("艺术类"));
        categoryList.add(bookService.findCategory("社科类"));
        categoryList.add(bookService.findCategory("生活类"));

        return categoryList;
    }


    @PostMapping ("/addBook")
    @ResponseBody
    public String addBook(@RequestBody Book book){
        System.out.println("添加书本"+book);
        bookService.addBook(book);
        return "成功添加";
    }

    @PostMapping("/updateBook")
    @ResponseBody
    public String updateBook(@RequestBody Book book){
        System.out.println("更新书本"+book);
        bookService.updateBook(book);
        return "成功更新书本信息";
    }

    @PostMapping("/deteleBook")
    @ResponseBody
    public String deteleBook(@RequestBody Map<String, Object> requestBody){
        int book_id = (int) requestBody.get("book_id");
        System.out.println("删除书本"+book_id);
        bookService.deleteBook(book_id);
        return "成功删除";
    }

    @GetMapping("/getBookSum")
    @ResponseBody
    public BookSum getBookSum(){
        int todayAdd=bookService.selectBookTodayAdd();
        int monthAdd=bookService.selectBookMonthAdd();
        int monthOut=bookService.selectBookMonthOut();
        return new BookSum(todayAdd,monthAdd,monthOut);
    }


    @GetMapping("/getMonthSaleList")
    @ResponseBody
    public List<Category> getMonthSaleList(){
        System.out.println("表现层--查询所有分类");
        // 调用service的方法
        List<Category> monthSaleList = new ArrayList<>(); // 初始化categoryList

        // 调用service的方法并添加到categoryList中
        monthSaleList.add(bookService.getMonthSaleList("文学类"));
        monthSaleList.add(bookService.getMonthSaleList("科学类"));
        monthSaleList.add(bookService.getMonthSaleList("历史类"));
        monthSaleList.add(bookService.getMonthSaleList("科普类"));
        monthSaleList.add(bookService.getMonthSaleList("艺术类"));
        monthSaleList.add(bookService.getMonthSaleList("社科类"));
        monthSaleList.add(bookService.getMonthSaleList("生活类"));

        return monthSaleList;
    }
}