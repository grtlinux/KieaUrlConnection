package org.tain.utils;

import java.util.HashMap;
import java.util.Map;

public class ApiConnection {

	private static boolean flag = true;
	
	public static void main(String[] args) throws Exception {
		if (flag) {
			Map<String, Object> root = new HashMap<>();
			root.put("url", "http://localhost:8080/v0.1/rest/custs");
			root.put("method", "get");
			root.put("method", "post");
			
			String response = HttpConnection.getResponse(root);
			System.out.println("--> response: " + response);
		}
		
		if (!flag) {
			Map<String, Object> root = new HashMap<>();
			root.put("url", "https://www.naver.com");
			
			String response = HttpConnection.getResponse(root);
			System.out.println("--> response: " + response);
		}
		
		if (!flag) {
			Map<String, Object> root = new HashMap<>();
			root.put("url", "http://bobr2.tistory.com/");
			
			String response = HttpConnection.getResponse(root);
			System.out.println("--> response: " + response);
		}
		
		if (!flag) {
			Map<String, Object> root = new HashMap<>();
			root.put("url", "https://selfsolve.apple.com/wcResults.do");
			root.put("methor", "post");
			root.put("request", "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345");
			
			String response = HttpConnection.getResponse(root);
			System.out.println("--> response: " + response);
		}
		
		if (!flag) {
			Map<String, Object> root = new HashMap<>();
			root.put("url", "http://bobr2.tistory.com/");
			root.put("methor", "post");
			root.put("request", "?Param1=aaaa&Param2=bbbb");
			
			String response = HttpConnection.getResponse(root);
			System.out.println("--> response: " + response);
		}
		
	}
}
