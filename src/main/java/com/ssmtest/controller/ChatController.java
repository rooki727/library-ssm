package com.ssmtest.controller;

import com.ssmtest.entity.Book;
import com.ssmtest.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @GetMapping("/getUniqueUserIds")
    @ResponseBody
    public List<Integer> getUniqueUserIds(){
        List<Integer> list=new ArrayList<>();
        list=chatService.getUniqueUserIds();
        return list;
    }
}
