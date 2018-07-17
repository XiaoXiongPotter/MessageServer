package com.dognessnetwork.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.dognessnetwork.websocket.socket.MyHandshakeInterceptor;
import com.dognessnetwork.websocket.socket.MyWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new MyWebSocketHandler(),"/chat").addInterceptors(new MyHandshakeInterceptor()).setAllowedOrigins("*"); //支持websocket 的访问链接
        registry.addHandler(new MyWebSocketHandler(),"/sockjs/chat").addInterceptors(new MyHandshakeInterceptor()).setAllowedOrigins("*").withSockJS(); //不支持websocket的访问链接

	}


}
