package com.ssmtest.controller;

import com.ssmtest.entity.*;
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
        try {
//调用service的方法
            List<Book> bookList = bookService.findAllBook();
            // 创建一个 List 存储 JSON 对象

            for(Book book: bookList){
//                // 去掉首尾的大括号，得到两个字典的字符串
                if(book.getPicture()!=null&&!book.getPicture().isEmpty()){
                    String[] dictStrs = book.getPicture().split(",");
                    List<Picture> pictureList = new ArrayList<>();
                    for (int i=0 ;i<dictStrs.length;i++){
                        Picture picture= new Picture(dictStrs[i]);
                        pictureList.add(picture);
                    }
                    book.setPictureList(pictureList);
                    pictureList=null;
                }
            }

            return bookList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping("/guessLike")
    @ResponseBody
    public ApiResponse<GuessLikeResponse>  getHomeGoodsGuessLike(
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize) {
        ApiResponse<GuessLikeResponse> response = new ApiResponse<>();
        try {
            response.setCode("1");
            response.setMsg("操作成功");
            List<Book> bookList=bookService.getGuessLikeBooks(page, pageSize);
            int totalPages = bookService.getTotalPages(pageSize);
            // 构建响应对象
            GuessLikeResponse guessLikeResponse = new GuessLikeResponse();
            guessLikeResponse.setBookList(bookList);
            guessLikeResponse.setTotalPages(totalPages);
            response.setResult(guessLikeResponse);
            return response;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }


    @GetMapping("/findCategory")
    @ResponseBody
    public List<Category> findCategory(){
        System.out.println("表现层--查询所有分类");
        // 调用service的方法
        List<Category> categoryList = new ArrayList<>(); // 初始化categoryList
        try {
            // 调用service的方法并添加到categoryList中
            categoryList.add(bookService.findCategory("文学类"));
            categoryList.add(bookService.findCategory("科学类"));
            categoryList.add(bookService.findCategory("历史类"));
            categoryList.add(bookService.findCategory("科普类"));
            categoryList.add(bookService.findCategory("艺术类"));
            categoryList.add(bookService.findCategory("社科类"));
            categoryList.add(bookService.findCategory("生活类"));

            return categoryList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @PostMapping ("/addBook")
    @ResponseBody
    public String addBook(@RequestBody Book book){
        System.out.println("添加书本"+book);
        try {
            bookService.addBook(book);
            return "成功添加";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/updateBook")
    @ResponseBody
    public String updateBook(@RequestBody Book book){
        System.out.println("更新书本"+book);
        try {
            bookService.updateBook(book);
            return "成功更新书本信息";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/deteleBook")
    @ResponseBody
    public String deteleBook(@RequestBody Map<String, Object> requestBody){
        int book_id = (int) requestBody.get("book_id");
        System.out.println("删除书本"+book_id);
        try {
            bookService.deleteBook(book_id);
            return "成功删除";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/getBookSum")
    @ResponseBody
    public BookSum getBookSum(){
        try {
            int todayAdd=bookService.selectBookTodayAdd();
            int monthAdd=bookService.selectBookMonthAdd();
            int monthOut=bookService.selectBookMonthOut();
            return new BookSum(todayAdd,monthAdd,monthOut);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @GetMapping("/getMonthSaleList")
    @ResponseBody
    public List<Category> getMonthSaleList(){
        System.out.println("表现层--查询所有分类");
        // 调用service的方法
        List<Category> monthSaleList = new ArrayList<>(); // 初始化categoryList
        try {
// 调用service的方法并添加到categoryList中
            monthSaleList.add(bookService.getMonthSaleList("文学类"));
            monthSaleList.add(bookService.getMonthSaleList("科学类"));
            monthSaleList.add(bookService.getMonthSaleList("历史类"));
            monthSaleList.add(bookService.getMonthSaleList("科普类"));
            monthSaleList.add(bookService.getMonthSaleList("艺术类"));
            monthSaleList.add(bookService.getMonthSaleList("社科类"));
            monthSaleList.add(bookService.getMonthSaleList("生活类"));
            return monthSaleList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
