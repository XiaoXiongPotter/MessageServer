package com.dognessnetwork.websocket.json;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.dognessnetwork.websocket.socket.MyWebSocketHandler;

@RestController
public class Push {

	private static final Logger logger = LoggerFactory.getLogger(Push.class);
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
 
	}

	/**
	 * 推送消息接口
	 * @param json
	 * @return
	 */
	@PostMapping("/pushMessageToUser")
	@ResponseBody
	public String pushMessageToUser(@RequestParam("json")String json){
		JSONObject js = JSON.parseObject(json);
		String imei = js.getString("imei");
		Map<String, WebSocketSession> map = MyWebSocketHandler.users;
			for(Entry<String, WebSocketSession> entry : map.entrySet()){
				logger.info("key:"+entry.getKey());
				logger.info("value:"+entry.getValue());
				logger.info("isOpen:"+entry.getValue().isOpen());
				if(entry.getKey().equals(imei) && entry.getValue().isOpen()){
					try {
						entry.getValue().sendMessage(new TextMessage(json));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					logger.info("pushMessageToUser:链接已经关闭");
				}
			}
		
		return "1000";
	}
	
	/**
	 * 推送消息接口
	 * @param json
	 * @return
	 */
	@PostMapping("/pushMessageToAll")
	@ResponseBody
	public String pushMessageToAll(@RequestParam("json")String json){
		
		String req = getRequest().getHeader("sessionId");
		//获取sessionId
		//String sessionId = (String) session.getAttribute("");
		Map<String, WebSocketSession> map = MyWebSocketHandler.users;
			for(Entry<String, WebSocketSession> entry : map.entrySet()){
				logger.info("key:"+entry.getKey());
				logger.info("value:"+entry.getValue());
				//if(entry.getKey().equals(sessionId)){
					try {
						entry.getValue().sendMessage(new TextMessage(json));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//}
			}
		
		return "1000";
	}
}
