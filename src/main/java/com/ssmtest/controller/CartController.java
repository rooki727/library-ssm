package com.ssmtest.controller;

import com.ssmtest.entity.ApiResponse;
import com.ssmtest.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/getCartCountById")
    @ResponseBody
    public ApiResponse<Integer> getCartCountById(@RequestBody Map<String, Object> requestBody){
        ApiResponse<Integer> response = new ApiResponse<>();
        int user_id = (int) requestBody.get("user_id");
        try {
           int count= cartService.getCartCountById(user_id);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(count);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
