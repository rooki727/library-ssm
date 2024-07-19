package com.ssmtest.controller;

import com.ssmtest.entity.ApiResponse;
import com.ssmtest.entity.BookCollection;
import com.ssmtest.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping("/collection")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @PostMapping("/getBookCollect")
    @ResponseBody
    public ApiResponse<BookCollection> getCartCountById(@RequestBody BookCollection bookCollection){
        ApiResponse<BookCollection> response = new ApiResponse<>();
        try {
            BookCollection bookCollection1 = collectionService.getBookCollect(bookCollection);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(bookCollection1);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/addCollection")
    @ResponseBody
    public ApiResponse<Boolean> addCollection(@RequestBody BookCollection bookCollection){
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            collectionService.addCollection(bookCollection);
            response.setCode("1");
            response.setMsg("操作收藏成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/deleteCollection")
    @ResponseBody
    public ApiResponse<Boolean> deleteCollection(@RequestBody BookCollection bookCollection){
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            collectionService.deleteCollection(bookCollection);
            response.setCode("1");
            response.setMsg("操作取消收藏成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
