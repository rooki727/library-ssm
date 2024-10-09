package com.ssmtest.service;

import com.ssmtest.entity.ChatMessage;

import java.util.List;

public interface ChatService {
    void insertMessage(ChatMessage chatMessage);

    List<ChatMessage> getMessagesByUserId(int userId);
}
