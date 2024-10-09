package com.ssmtest.service.impl;

import com.ssmtest.dao.ChatDao;
import com.ssmtest.entity.ChatMessage;
import com.ssmtest.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("chatService")
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatDao chatDao;
    @Override
    public void insertMessage(ChatMessage chatMessage) {
        chatDao.insertMessage(chatMessage);
    }

    @Override
    public List<ChatMessage> getMessagesByUserId(int userId) {
        return chatDao.getMessagesByUserId(userId);
    }
}
