package com.websocket.socket;

import java.net.InetAddress;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;


public class MyWebSocketHandler implements WebSocketHandler {
    private static final Logger logger = Logger.getLogger(MyWebSocketHandler.class);
    //public static final Vector <WebSocketSession> users = new Vector <>();
    public static final Map<String, WebSocketSession> users = new IdentityHashMap<>();
    //初次链接成功执行
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("链接成功......");
//        users.add(webSocketSession);
//        String id = (String) webSocketSession.getAttributes().get(SystemContant.WEBSOCKET_ID);
//        System.out.println("socketId:"+id);
        /*String id = (String) webSocketSession.getAttributes().get(SystemContant.WEBSOCKET_ID);
        if(id!= null){
            webSocketSession.sendMessage(new TextMessage(id + ""));
        }*/
    }

    //接受消息处理消息
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception { 
//    	String id = (String) webSocketSession.getAttributes().get(SystemContant.WEBSOCKET_ID);
    	logger.info("接收信息..."+webSocketMessage.getPayload());
    	String message = webSocketMessage.getPayload().toString();
    	System.out.println(message);
    	//获取项目所在服务器地址
    	String url = MyHandshakeInterceptor.host+":"+MyHandshakeInterceptor.port;
    	//TODO 调用用户信息修改接口保存链接socket所在的服务器
    	JSONObject json = JSONObject.parseObject(message);
    	String sessionId =json.getString("sessionId");
    	logger.info("sessionId:"+sessionId);
    	users.put(sessionId,webSocketSession);
    	webSocketSession.sendMessage(new TextMessage("链接成功"));
    	//sendMessageToUser(id,new TextMessage(webSocketMessage.getPayload() + ""));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        logger.info("链接出错，关闭链接......");
        //移除socket链接
        for(Entry<String, WebSocketSession> entry:users.entrySet()){
        	if(entry.getValue().getId().equals(webSocketSession.getId())){
        		users.remove(entry.getKey());
        	}
        }
        
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("链接关闭......");
        users.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
  /*  public void sendMessageToUser(String id, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(SystemContant.WEBSOCKET_ID).equals(id)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }*/
    
    
    /**
     * 
     *
     * @param id
     * @param message
     */
/*    public static void sendAddMessage(String id, String message) {
    	TextMessage textMessage = new TextMessage(message);
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(SystemContant.WEBSOCKET_ID).equals(id)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(textMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }*/
}