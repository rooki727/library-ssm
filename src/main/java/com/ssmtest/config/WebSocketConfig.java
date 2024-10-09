package com.ssmtest.config;
import com.ssmtest.webSocket.ChatWebSocketHandler;
import com.ssmtest.webSocket.MyHandShakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig  implements WebSocketConfigurer {
    @Autowired
    private ChatWebSocketHandler chatWebSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {

        //添加websocket处理器，添加握手拦截器
        webSocketHandlerRegistry.addHandler(chatWebSocketHandler, "/chat").setAllowedOrigins("*");

        //添加websocket处理器，添加握手拦截器
        webSocketHandlerRegistry.addHandler(chatWebSocketHandler, "/chat/sockjs").withSockJS();
    }
}
