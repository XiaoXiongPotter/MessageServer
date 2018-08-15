package com.dognessnetwork.websocket.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestClass {

	public static void main(String[] args) {
	    try {
	             InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址 //PC-20140317PXKX/192.168.0.121
	             System.out.println(address.getHostAddress());
	        } catch (UnknownHostException e) { 
	             e.printStackTrace();
	      } 
	 }
}
