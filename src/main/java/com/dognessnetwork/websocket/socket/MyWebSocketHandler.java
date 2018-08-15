package com.dognessnetwork.websocket.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;
import com.dognessnetwork.websocket.constant.Constants;
import com.dognessnetwork.websocket.util.HttpUtility;


public class MyWebSocketHandler implements WebSocketHandler {
    private static final Logger logger = Logger.getLogger(MyWebSocketHandler.class);
    //public static final Vector <WebSocketSession> users = new Vector <>();
    public static final Map<String, WebSocketSession> users = new IdentityHashMap<>();
    //初次链接成功执行
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("链接成功......");
    }

    //接受消息处理消息
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception { 
    	logger.info("接收信息..."+webSocketMessage.getPayload());
    	String message = webSocketMessage.getPayload().toString();
    	System.out.println(message);
    	//获取项目所在服务器地址
    	String url = webSocketSession.getHandshakeHeaders().get("host").get(0)+"/messageServer";
    	logger.info("服务器地址："+url);
    	//TODO 调用用户信息修改接口保存链接socket所在的服务器
    	String serverUrl = Constants.SERVER_CONTEXT_DOMAIN+Constants.SERVER_CONTEXT_PATH+"/base/necklace/updateNecklace";
        logger.info("clientServer的url为："+serverUrl);
    	JSONObject json = JSONObject.parseObject(message);
    	String sessionId =json.getString("sessionId");
    	String deviceCode =json.getString("deviceCode");
    	SortedMap<Object, Object> parameters = new TreeMap<>();
    	parameters.put("deviceCode", deviceCode);
    	parameters.put("domain", url);
    	logger.info("sessionId:"+sessionId+" deviceCode:"+deviceCode);
    	String result = HttpUtility.PostMessage(serverUrl, parameters, 2000,sessionId);
    	logger.info("result:"+result);
    	put(sessionId,webSocketSession);
    }
    
    private static synchronized void put(String sessionId,WebSocketSession webSocketSession){
    	users.put(sessionId,webSocketSession);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        logger.info("链接出错，关闭链接......");
        //移除socket链接
        for(Entry<String, WebSocketSession> entry:users.entrySet()){
        	logger.info("移除sessionId："+webSocketSession.getId());
        	if(entry.getValue().getId().equals(webSocketSession.getId())){
        		logger.info("移除sessionKey："+entry.getKey());
        		logger.info("移除sessionKey的SessionId："+entry.getValue().getId());
        		users.remove(entry.getKey());
        	}
        }
        
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("链接关闭......");
        for(Entry<String, WebSocketSession> entry:users.entrySet()){
        	logger.info("移除sessionId："+webSocketSession.getId());
        	if(entry.getValue().getId().equals(webSocketSession.getId())){
        		logger.info("移除sessionKey："+entry.getKey());
        		users.remove(entry.getKey());
        	}
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 获取服务器ip
     * @return
     */
    public String getIp(){
    	String host = null;
    	 try {
			InetAddress address = InetAddress.getLocalHost();
			host = address.getHostAddress();
			logger.info("服务器域名："+host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return host;
    }
}