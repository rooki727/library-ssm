package com.ssmtest.controller;

import com.ssmtest.entity.ApiResponse;
import com.ssmtest.entity.Logistics;
import com.ssmtest.entity.LogisticsPageRes;
import com.ssmtest.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("/logistics")
public class LogisticsController {
    @Autowired
    private LogisticsService logisticsService;
    @GetMapping("/findAllLogistics")
    @ResponseBody
    public ApiResponse<LogisticsPageRes> findAllLogistics(@RequestParam("page") int page,
                                                          @RequestParam("pageSize") int pageSize){
        ApiResponse<LogisticsPageRes>  response=new ApiResponse<>();
        List<Logistics> logisticsList=new ArrayList<>();
        try {
            int totalPage=logisticsService.getTotalPageLogistics();
            logisticsList=logisticsService.findAllLogistics(page, pageSize);
            LogisticsPageRes logisticsPageRes=new LogisticsPageRes();
            logisticsPageRes.setLogisticsList(logisticsList);
            logisticsPageRes.setTotalPage(totalPage);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(logisticsPageRes);
            return  response;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/findLogisticsById")
    @ResponseBody
    public ApiResponse<List<Logistics>> findLogisticsById(@RequestParam("order_id") int order_id){
        ApiResponse<List<Logistics>>  response=new ApiResponse<>();

        try {
           List<Logistics> logisticsList= logisticsService.findLogisticsById(order_id);

            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(logisticsList);
            return  response;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/deleteLogistics")
    @ResponseBody
    public ApiResponse<Boolean> deleteLogistics(@RequestBody Map<String, Object> requestBody){
        ApiResponse<Boolean>  response=new ApiResponse<>();
        int logistics_id = (int) requestBody.get("logistics_id");

        try {
            logisticsService.deleteLogistics(logistics_id);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @PostMapping("/addLogistics")
    @ResponseBody
    public ApiResponse<Boolean> addLogistics(@RequestBody Logistics logistics){
        ApiResponse<Boolean>  response=new ApiResponse<>();

        try {
            logisticsService.addLogistics(logistics);
            response.setCode("1");
            response.setMsg("操作成功");
            response.setResult(true);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
