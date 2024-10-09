package com.ssmtest.dao;

import com.ssmtest.entity.ChatMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatDao {
    void insertMessage(ChatMessage chatMessage);

    List<ChatMessage> getMessagesByUserId(int userId);
}
