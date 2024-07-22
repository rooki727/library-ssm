package com.ssmtest.controller;

import com.ssmtest.entity.ApiResponse;
import com.ssmtest.entity.Cart;
import com.ssmtest.entity.CartBook;
import com.ssmtest.service.BookService;
import com.ssmtest.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/findBookCartByUserId")
    @ResponseBody
    public ApiResponse<List<CartBook>> findBookCartByUserId(@RequestBody Map<String, Object> requestBody){
        ApiResponse<List<CartBook>> response = new ApiResponse<>();
        int user_id = (int) requestBody.get("user_id");
        try {
            List<CartBook> cartBookList= cartService.findBookCartByUserId(user_id);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(cartBookList);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/updateCartSelect")
    @ResponseBody
    public ApiResponse<Boolean> updateCartSelect(@RequestBody Cart cart){
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            cartService.updateCartSelect(cart);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/updateCartNumber")
    @ResponseBody
    public ApiResponse<Boolean> updateCartNumber(@RequestBody Cart cart){
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            cartService.updateCartNumber(cart);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/deleteMemberCart")
    @ResponseBody
    public ApiResponse<Boolean> deleteMemberCart(@RequestBody Map<String, Object> requestBody){
        ApiResponse<Boolean> response = new ApiResponse<>();
        int cart_id = (int) requestBody.get("cart_id");
        try {
            cartService.deleteMemberCart(cart_id);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addBookCart")
    @ResponseBody
    public ApiResponse<Boolean> addBookCart(@RequestBody Cart cart){
        ApiResponse<Boolean> response = new ApiResponse<>();
        try {
            Cart cartFind=cartService.findCartByUserBook(cart);
            if(cartFind!=null){
             cart.setCart_id(cartFind.getCart_id());
//                存在时更新数量
                cartService.AddCartNumber(cart);
            }
            else {
//                不存在时添加
                cartService.addBookCart(cart);
            }
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
