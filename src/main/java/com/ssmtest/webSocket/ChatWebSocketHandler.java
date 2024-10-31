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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
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
    // 解析查询字符串到Map中
    private Map<String, String> parseQueryParams(String query) throws UnsupportedEncodingException {
        Map<String, String> queryParams = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                queryParams.put(key, value);
            }
        }
        return queryParams;
    }
//    握手实现后
@Override
public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    // 获取请求的URI
    URI uri = session.getUri();
    // 解析查询参数
    String query = uri.getQuery();
    Map<String, String> queryParams = parseQueryParams(query);
    String userIdString = queryParams.get("user_id");
    String adminIdString=queryParams.get("admin_id");

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
    if (adminIdString != null && !adminIdString.isEmpty()) {
        try {
            userSocketSessionMap.put(adminIdString, session); // 将用户ID与Session存储到map中
            System.out.println("客服ID " + adminIdString + " 与 WebSocketSession " + session.getId() + " 已存储到 map 中");
        } catch (NumberFormatException e) {
            System.out.println("用户ID格式错误: " + adminIdString);
        }
    } else {
        System.out.println("无法获取用户ID，Session属性中没有 'admin_id'");
    }
}

    // 广播发送消息给所有用户
    public void sendMessageToAllUsers(TextMessage message) {
        for (WebSocketSession session : userSocketSessionMap.values()) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(message);
                } catch (Exception e) {
                    // 日志记录或者处理异常
                    System.err.println("Error sending message to user: " + e.getMessage());
                    // 可能需要关闭会话或者进行其他清理工作
                }
            }
        }
    }
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception{
        if(webSocketMessage.getPayloadLength()==0) return;
        ChatMessage chatMessage=new ChatMessage();
        // 获取请求的URI
        URI uri = webSocketSession.getUri();
        // 解析查询参数
        String query = uri.getQuery();
        Map<String, String> queryParams = parseQueryParams(query);
        String userIdString = queryParams.get("user_id");
        String adminIdString=queryParams.get("admin_id");
        Date date=new Date();
        String msg=webSocketMessage.getPayload().toString();
        //得到Socket通道中的数据并转化为Message对象
        try {
            // 将 userIdString 转换为 int 类型
            int userId = 0;
            if (userIdString != null) {
                userId = Integer.parseInt(userIdString);
                chatMessage.setUser_id(userId);
            }
            int adminId = 0;
            if (adminIdString != null) {
                adminId = Integer.parseInt(adminIdString);
                chatMessage.setAdmin_id(adminId);
            }
            chatMessage.setSendTime(date);
            chatMessage.setMessage(msg);
            //将信息保存至数据库
            chatService.insertMessage(chatMessage);
//            webSocketSession.sendMessage(new TextMessage(chatMessage.toString()));
//            重写发送回去
            sendMessageToAllUsers(new TextMessage(chatMessage.toString()));
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
            if (entry.getValue().getAttributes().get("user_id") == webSocketSession.getAttributes().get("user_id")) {
                userSocketSessionMap.remove(userId);
                System.out.println("WebSocket in staticMap:" + userId + "removed");
            }
        }
    }

    public boolean supportsPartialMessages() {
        return false;
    }


}
