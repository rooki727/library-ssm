package com.ssmtest.controller;

import com.ssmtest.entity.Summary;
import com.ssmtest.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/summary")
public class SummaryController {
    @Autowired
    private SummaryService summaryService;

    @GetMapping("/findAllSummary")
    @ResponseBody
    public List<Summary> findAllSummary(){
        System.out.println("表现层--查询所有订单");
        try {
            //调用service的方法
            List<Summary> summaryList = summaryService.findAllSummary();
            return summaryList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/addSummary")
    @ResponseBody
    public String addSummary(@RequestBody Summary summary){
        try {
            summaryService.addSummary(summary);
            return  "成功汇总订单";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
