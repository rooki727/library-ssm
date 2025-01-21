package com.ssmtest.controller;


import com.ssmtest.dao.UserBookDao;
import com.ssmtest.entity.ApiResponse;
import com.ssmtest.entity.Book;
import com.ssmtest.entity.GuessLikeResponse;
import com.ssmtest.service.BookService;
import com.ssmtest.service.impl.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/recommend")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;
    @Autowired
    private BookService bookService;
    @GetMapping("/getRecommendations")
    @ResponseBody
    public ApiResponse<GuessLikeResponse> getRecommendations(@RequestParam int user_id,@RequestParam int page,@RequestParam int pageSize) {
        ApiResponse<GuessLikeResponse> response = new ApiResponse<>();
        List<Book> bookList=recommendationService.recommendBooks(user_id,page,pageSize);
        int totalPages = bookService.getTotalPages(pageSize);
        // 构建响应对象
        GuessLikeResponse guessLikeResponse = new GuessLikeResponse();
        guessLikeResponse.setBookList(bookList);
        guessLikeResponse.setTotalPages(totalPages);
        response.setCode("1");
        response.setMsg("操作成功");
        response.setResult(guessLikeResponse);
        return response;
    }
}
