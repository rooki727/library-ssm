package com.ssmtest.webSocket;

import com.ssmtest.entity.ChatMessage;
import com.ssmtest.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ChatWebSocketHandler  extends TextWebSocketHandler {
    @Autowired
    private ChatService chatService;

//    创建map 存储ws
    public static final Map<String,WebSocketSession> userSocketSessionMap
        ;

    static {
        userSocketSessionMap= new HashMap<String,WebSocketSession>();
    }

//    握手实现后
@Override
public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    HttpHeaders headers = session.getHandshakeHeaders();
    String userIdString = headers.getFirst("user_id");


    if (userIdString != null && !userIdString.isEmpty()) {
        try {
            userSocketSessionMap.put(userIdString, session); // 将用户ID与Session存储到map中
            System.out.println("用户ID " + userIdString + " 与 WebSocketSession " + session.getId() + " 已存储到 map 中");
            int userId = 0;
            userId = Integer.parseInt(userIdString);
            List<ChatMessage> chatMessageList= chatService.getMessagesByUserId(userId);
            session.sendMessage(new TextMessage(chatMessageList.toString()));
        } catch (NumberFormatException e) {
            System.out.println("用户ID格式错误: " + userIdString);
        }
    } else {
        System.out.println("无法获取用户ID，Session属性中没有 'user_id'");
    }
}



    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception{
        if(webSocketMessage.getPayloadLength()==0) return;
        ChatMessage chatMessage=new ChatMessage();
        HttpHeaders headers = webSocketSession.getHandshakeHeaders();
        String userIdString  = headers.getFirst("user_id");

        Date date=new Date();
        String msg=webSocketMessage.getPayload().toString();
        //得到Socket通道中的数据并转化为Message对象
        try {
            // 将 userIdString 转换为 int 类型
            int userId = 0;
            if (userIdString != null) {
                userId = Integer.parseInt(userIdString);
            }
            chatMessage.setUser_id(userId);
            chatMessage.setSendTime(date);
            chatMessage.setMessage(msg);
            //将信息保存至数据库
            chatService.insertMessage(chatMessage);
        } catch (NumberFormatException e) {
            System.err.println("Invalid user ID format: " + userIdString);
        }
        //发送Socket信息
//        sendMessageToUser(msg.getUser_id(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
    }

    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        HttpHeaders headers = webSocketSession.getHandshakeHeaders();
        String userId = headers.getFirst("user_id");
        System.out.println("WebSocket:"+userId+"close connection");
        for (Map.Entry<String, WebSocketSession> entry : userSocketSessionMap.entrySet()) {
            if (entry.getValue().getAttributes().get("uid") == webSocketSession.getAttributes().get("uid")) {
                userSocketSessionMap.remove(userId);
                System.out.println("WebSocket in staticMap:" + userId + "removed");
            }
        }
    }

    public boolean supportsPartialMessages() {
        return false;
    }

    //发送信息的实现

}
