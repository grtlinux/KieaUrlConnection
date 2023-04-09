package org.tain.d20230407.test;

import java.util.HashMap;
import java.util.Map;

import org.tain.d20230407.httpclient.TainHttpUrlConnection;
import org.tain.d20230407.type.HttpInfo;

/*
 *   header.put("Content-Type", "application/x-www-form-urlencoded");
 *   header.put("Content-Type", "multipart/form-data");

 */
public class TestMain {

	public static void main(String[] args) {
		System.out.println("main: start ...");
		
		if (Boolean.TRUE) test01_httpGet();
		if (Boolean.TRUE) test02_httpPost();
		if (Boolean.TRUE) test03_httpsGet();
		if (Boolean.TRUE) test04_httpsPost();
		
		System.out.println("main: finish ...");
	}
	
	private static void test01_httpGet() {
		System.out.println("-------------------> test01_httpGet: start ...");
		
		try {
			HttpInfo info = new HttpInfo();
			
			info.setUrl("http://127.0.0.1:8000");
			info.setPath("/get/123");
			info.setMethod("GET");
			
			Map<String,String> header = new HashMap<>();
			header.put("Content-Type", "application/json");
			header.put("header-param1", "value-1");
			info.setHeader(header);
			
			Map<String,String> params = new HashMap<>();
			params.put("param1", "value1");
			params.put("param2", "123");
			info.setParams(params);
			
			// request -> response
			info = new TainHttpUrlConnection().connect(info);
			
			if (info.getStatusCode() == 200) {
				System.out.println(">>> " + info.getResponse().toString());
			} else {
				System.out.println(">>> statuscode: " + info.getStatusCode());
				System.out.println(">>> message: " + info.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("test01_httpGet: finish ...");
		}
	}

	private static void test02_httpPost()  {
		System.out.println("-------------------> test02_httpPost: start ...");
		
		try {
			HttpInfo info = new HttpInfo();
			
			info.setUrl("http://127.0.0.1:8000");
			info.setPath("/v1/post");
			info.setMethod("POST");
			
			Map<String,String> header = new HashMap<>();
			header.put("Content-Type", "application/json");
			header.put("header-param1", "value-1");
			info.setHeader(header);
			
			Map<String,String> params = new HashMap<>();
			params.put("param1", "value1");
			params.put("param2", "12345678");
			info.setParams(params);
			
			// request -> response
			info = new TainHttpUrlConnection().connect(info);
			
			if (info.getStatusCode() == 200) {
				System.out.println(">>> " + info.getResponse().toString());
			} else {
				System.out.println(">>> statuscode: " + info.getStatusCode());
				System.out.println(">>> message: " + info.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("test02_httpPost: finish ...");
		}
	}
	
	private static void test03_httpsGet() {
		System.out.println("-------------------> test03_httpsGet: start ...");
		
		try {
			HttpInfo info = new HttpInfo();
			
			info.setUrl("https://www.naver.com");
			info.setPath("/");
			info.setMethod("GET");
			
			Map<String,String> header = new HashMap<>();
			header.put("Content-Type", "application/json");
			header.put("header-param1", "value-1");
			info.setHeader(header);
			
			Map<String,String> params = new HashMap<>();
//			params.put("param1", "value1");
//			params.put("param2", "12345678");
			info.setParams(params);
			
			// request -> response
			info = new TainHttpUrlConnection().connect(info);
			
			if (info.getStatusCode() == 200) {
				System.out.println(">>> " + info.getResponse().toString());
			} else {
				System.out.println(">>> statuscode: " + info.getStatusCode());
				System.out.println(">>> message: " + info.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("test03_httpsGet: finish ...");
		}
		
	}
	
	private static void test04_httpsPost() {
		System.out.println("-------------------> test04_httpsPost: start ...");
		
		try {
			HttpInfo info = new HttpInfo();
			
			info.setUrl("https://www.github.com"); 
			info.setPath("/");
			info.setMethod("POST");
			
			Map<String,String> header = new HashMap<>();
			header.put("Content-Type", "application/json");
			header.put("header-param1", "value-1");
			info.setHeader(header);
			
			Map<String,String> params = new HashMap<>();
//			params.put("param1", "value1");
//			params.put("param2", "12345678");
			info.setParams(params);
			
			// request -> response
			info = new TainHttpUrlConnection().connect(info);
			
			if (info.getStatusCode() == 200) {
				System.out.println(">>> " + info.getResponse().toString());
			} else {
				System.out.println(">>> statuscode: " + info.getStatusCode());
				System.out.println(">>> message: " + info.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("test04_httpsPost: finish ...");
		}
	}
}
