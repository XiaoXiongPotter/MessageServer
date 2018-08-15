package com.dognessnetwork.websocket.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author 冯浩员
 * @date 2017年8月25日
 * @description
 * @remark
 */
public class HttpUtility {
    public static final Logger log = LoggerFactory.getLogger(HttpUtility.class);

	/**
	 * 
	 * @param url
	 * @param data 例如：a=x&b=x
	 * @param connectTimeout 可为null
	 * @return
	 * @throws IOException
	 */
	public static String UrlEncodedPost(String url, String data, Integer connectTimeout) throws IOException {
		URL postUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		if(connectTimeout!=null){
			connection.setConnectTimeout(connectTimeout);
		}		
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.writeBytes(data);
		out.flush();
		out.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
		String line = "";
		String response = "";
		while ((line = reader.readLine()) != null) {
			response = line;
		}
		reader.close();
		connection.disconnect();
		return response;
	}


	/**
	 * 
	 * @param url
	 * @param parameters
	 * @param connectTimeout 可为null
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static String PostMessage(String url, SortedMap<Object,Object> parameters, Integer connectTimeout,String token) throws IOException {
		URL postUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = entry.getKey().toString();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				if (!sb.toString().equals("")){
					sb.append("&");
				}

				if(v.getClass().equals(Boolean.class) || v.getClass().equals(BigDecimal.class) || v.getClass().equals(Integer.class)) {
					sb.append(k + "=" + v);
				} else {
					sb.append(k + "=" + URLEncoder.encode(v.toString(), "UTF-8"));
				}
			}
		}
		String param = sb.toString();
//		String sign = SignUtils.creatSign(parameters);

		connection.setRequestProperty("token", token);
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		if(connectTimeout!=null){
			connection.setConnectTimeout(connectTimeout);
		}
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());

		out.writeBytes(param);
		out.flush();
		out.close();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
		String line = "";
		String response = "";
		while ((line = reader.readLine()) != null) {
			response = line;
		}
		reader.close();
		connection.disconnect();
		log.info(url+" "+response+" "+param);
		return response;
	}
	

}
