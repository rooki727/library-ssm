package com.ssmtest.controller;

import com.ssmtest.entity.Address;
import com.ssmtest.entity.ApiResponse;
import com.ssmtest.entity.GuessLikeResponse;
import com.ssmtest.entity.User;
import com.ssmtest.service.AddressService;
import com.ssmtest.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private IUserService userService;
    @PostMapping("/getAddressListById")
    @ResponseBody
    public ApiResponse<List<Address>> getAddressListById(@RequestBody Map<String, Object> requestBody) {
        ApiResponse<List<Address>> response = new ApiResponse<>();
        int user_id = (int) requestBody.get("user_id");
        try {
           List<Address> addressList=addressService.getAddressListById(user_id);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(addressList);
            return response;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
//    删除地址
     @PostMapping("/deleteAddressById")
     @ResponseBody
    public  ApiResponse<Boolean> deleteAddressById(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
         ApiResponse<Boolean> response = new ApiResponse<>();
        //        获取id
         int user_id = (int) requestBody.get("user_id");
         int address_id = (int) requestBody.get("address_id");
         //处理token
         // 获取请求头中的Token
         String tokenAuthorization = request.getHeader("Authorization");
         // 判断是否是Bearer Token格式
         if (tokenAuthorization.startsWith("Bearer ")) {

             // 获取实际的Token值
             String token = tokenAuthorization.substring(7);
             User oldUser = userService.getLoginCommonById(user_id);// 7是 "Bearer " 的长度
             boolean isToken = userService.isCommonToken(token, oldUser.getToken());
             if (isToken) {
                 System.out.println("token校验成功！deleteAddressById");
                 try {
                     addressService.deleteAddressById(address_id);
                     response.setCode("1");
                     response.setMsg("操作成功");
                     response.setResult(true);
                     return response;
                 }catch (Exception e) {
                    throw new RuntimeException(e);
                 }
                 }
             }
         return null;
         }
//添加address
    @PostMapping("/addAddress")
    @ResponseBody
    public  ApiResponse<Boolean> addAddress(@RequestBody Address address, HttpServletRequest request) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        //        获取id
        int user_id = address.getUser_id();
        //处理token
        // 获取请求头中的Token
        String tokenAuthorization = request.getHeader("Authorization");
        // 判断是否是Bearer Token格式
        if (tokenAuthorization.startsWith("Bearer ")) {
            // 获取实际的Token值
            String token = tokenAuthorization.substring(7);
            User oldUser = userService.getLoginCommonById(user_id);// 7是 "Bearer " 的长度
            boolean isToken = userService.isCommonToken(token, oldUser.getToken());
            if (isToken) {
                System.out.println("token校验成功！addAddress");
                try {
                    addressService.addAddress(address);
                    response.setCode("1");
                    response.setMsg("操作成功");
                    response.setResult(true);
                    return response;
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

//    获取地址通过id
    @PostMapping("/getAddressById")
    @ResponseBody
    public  ApiResponse<Address> getAddressById(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        ApiResponse<Address> response = new ApiResponse<>();
        //        获取id
        int user_id = (int) requestBody.get("user_id");
        int address_id = (int) requestBody.get("address_id");
        //处理token
        // 获取请求头中的Token
        String tokenAuthorization = request.getHeader("Authorization");
        // 判断是否是Bearer Token格式
        if (tokenAuthorization.startsWith("Bearer ")) {
            // 获取实际的Token值
            String token = tokenAuthorization.substring(7);
            User oldUser = userService.getLoginCommonById(user_id);// 7是 "Bearer " 的长度
            boolean isToken = userService.isCommonToken(token, oldUser.getToken());
            if (isToken) {
                System.out.println("token校验成功！getAddressById");
                try {
                    Address address=addressService.getAddressById(address_id);
                    response.setCode("1");
                    response.setMsg("操作成功");
                    response.setResult(address);
                    return response;
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

//    updateAddress
@PostMapping("/updateAddress")
@ResponseBody
public  ApiResponse<Boolean> updateAddress(@RequestBody Address address, HttpServletRequest request) {
    ApiResponse<Boolean> response = new ApiResponse<>();
    //        获取id
    int user_id = address.getUser_id();

    //处理token
    // 获取请求头中的Token
    String tokenAuthorization = request.getHeader("Authorization");
    // 判断是否是Bearer Token格式
    if (tokenAuthorization.startsWith("Bearer ")) {
        // 获取实际的Token值
        String token = tokenAuthorization.substring(7);
        User oldUser = userService.getLoginCommonById(user_id);// 7是 "Bearer " 的长度
        boolean isToken = userService.isCommonToken(token, oldUser.getToken());
        if (isToken) {
            System.out.println("token校验成功！updateAddress");
            try {
                addressService.updateAddress(address);
                response.setCode("1");
                response.setMsg("操作成功");
                response.setResult(true);
                return response;
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    return null;
}
    //    updateAddress
    @PostMapping("/updateAddressIsDefault")
    @ResponseBody
    public  ApiResponse<Boolean> updateAddressIsDefault(@RequestBody Address address, HttpServletRequest request) {
        ApiResponse<Boolean> response = new ApiResponse<>();
        //        获取id
        int user_id = address.getUser_id();
        //处理token
        // 获取请求头中的Token
        String tokenAuthorization = request.getHeader("Authorization");
        // 判断是否是Bearer Token格式
        if (tokenAuthorization.startsWith("Bearer ")) {
            // 获取实际的Token值
            String token = tokenAuthorization.substring(7);
            User oldUser = userService.getLoginCommonById(user_id);// 7是 "Bearer " 的长度
            boolean isToken = userService.isCommonToken(token, oldUser.getToken());
            if (isToken) {
                System.out.println("token校验成功！updateAddressIsDefault");
                try {
                    addressService.updateAddressIsDefault(address);
                    response.setCode("1");
                    response.setMsg("更新默认成功！");
                    response.setResult(true);
                    return response;
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}