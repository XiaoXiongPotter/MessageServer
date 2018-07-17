package com.websocket.socket;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class MyHandshakeInterceptor implements HandshakeInterceptor{

	public static int port;
	public static String host;
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		// TODO Auto-generated method stub
		if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            //获取部署服务器的第ip与端口号
            port = servletRequest.getServerPort();
            host = servletRequest.getLocalAddr();
            	//String id = servletRequest.getSession().getId();    
            	//System.out.println("getSessionId:"+id);
//            	attributes.put(SystemContant.WEBSOCKET_ID,id);
//                servletRequest.getSession().setAttribute(SystemContant.WEBSOCKET_ID, id); 
        }
        return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		
	}

}
