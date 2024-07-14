package com.ssmtest.controller;

import com.ssmtest.entity.Address;
import com.ssmtest.entity.ApiResponse;
import com.ssmtest.entity.GuessLikeResponse;
import com.ssmtest.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

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


}
