package com.ssmtest.exception;

import com.ssmtest.entity.ApiResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ApiResponse<Object> handleException(Exception e) {
//        ApiResponse<Object> response = new ApiResponse<>();
//        response.setCode("-1"); // Example error code
//        response.setMsg("操作失败：" + e.getMessage()); // Example error message
//        response.setResult(null); // Or handle differently based on your needs
//        return response;
//    }
//}

