package org.tain.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpConnection {

	private static boolean flag = true;
	
	private static final int CONN_TIMEOUT = 30;
	private static final int READ_TIMEOUT = 10;
	//private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.75 Safari/535.7";
	private static final String USER_AGENT = "Mozilla/5.0";
	
	public static String getResponse(final Map<String,Object> root) {
		String url = (String)root.get("url");
		String method = (String)root.getOrDefault("method","get");
		method = method.toUpperCase();
		if (flag) System.out.printf("----> url: %s, method: %s\n", url, method);
		
		HttpURLConnection con = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod(method);
			
			if (flag) {
				con.setDefaultUseCaches(true);
				con.setConnectTimeout(CONN_TIMEOUT * 1000);
				con.setReadTimeout(READ_TIMEOUT * 1000);
				System.out.println("----> getDefaultUseCaches(): " + con.getDefaultUseCaches());
				System.out.println("----> getConnectTimeout(): " + con.getConnectTimeout());
				System.out.println("----> getReadTimeout(): " + con.getReadTimeout());
			}
			
			if (true) {
				con.setRequestProperty("Content-Type", "application/json; utf-8");
				con.setRequestProperty("Accept", "application/json");
				
				@SuppressWarnings("unchecked")
				Map<String,Object> properties = (Map<String,Object>) root.get("properties");
				if (properties != null) {
					for (Map.Entry<String, Object> entry : properties.entrySet()) {
						con.setRequestProperty(entry.getKey(), (String) entry.getValue());
					}
					System.out.println("----> properties: " + properties);
				}
			}
			
			if (!flag) {
				// authorization
				String auth = "username:password";
				String encodedBytes = Base64.getEncoder().encodeToString(auth.getBytes());
				String authorization = "Basic " + encodedBytes;
				con.setRequestProperty("Authorization", authorization);
				System.out.println("----> authorization: " + authorization);
			}
			
			String request = null;
			if (flag) {
				request = (String) root.get("request");
				if (request == null || "".equals(request))
					request = "";
				con.setRequestProperty("Content-Length", String.valueOf(request.length()));
			}
			
			if (flag) {
				// send req
				con.setDoOutput(true);
				//con.getOutputStream().write(strRequest.getBytes("utf-8")); // POST 호출
				DataOutputStream dos = new DataOutputStream(con.getOutputStream());
				dos.writeBytes(request);
				dos.flush();
				dos.close();
				System.out.printf("----> request(%d): %s\n", request.length(), request);
			}
			
			if (flag) {
				System.out.println("----> header: " + con.getHeaderFields());
				//System.out.println("---> properties: " + con.getRequestProperties());
			}
			
			if (flag) {
				int resCode = con.getResponseCode();
				System.out.println("----> resCode: " + resCode);
			}
			
			if (flag) {
				// recv res
				//con.setDoInput(true);
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					//sb.append(line);
					sb.append(line).append('\n');
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.disconnect();
				if (flag) System.out.println("----> disconnect()");
			}
		}
		
		System.out.println("----> response: " + sb.toString());
		return sb.toString();
	}
	
	/////////////////////////////////////////////////////
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> root = null;
		if (flag) {
			root = new HashMap<>();
			root.put("url", "http://localhost:8080/v0.1/rest/custs");
			if (!flag) root.put("method", "get");
			
			if (!flag) {
				Map<String, Object> header = new HashMap<>();
				root.put("header", header);
			}
			if (!flag) {
				Map<String, Object> properties = new HashMap<>();
				properties.put("User-Agent", USER_AGENT);
				properties.put("Connection", "keep-alive"); 
				properties.put("Content-Type", "application/x-www-form-urlencoded");
				properties.put("Content-Type", "application/json; charset=utf-8");
				properties.put("Content-Type", "application/json; utf-8");
				properties.put("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4"); 
				properties.put("Accept-Encoding", "gzip,deflate,sdch"); 
				properties.put("Accept-Charset", "windows-949,utf-8;q=0.7,*;q=0.3"); 
				properties.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				properties.put("Accept", "application/json");
				properties.put("Cookie", "1234567890");
				root.put("properties", properties);
			}
			if (flag) {
				String request = null;
				request = "";
				//request = "{\"code\":\"C001\",\"dummy\":\"dummyValue\"}";
				root.put("request", request);
				System.out.println("--> request: " + request);
			}
		}
		
		String strRes = HttpConnection.getResponse(root);
		if (flag) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode objJson = objectMapper.readTree(strRes);
			System.out.println("--> response1: " + objectMapper.writeValueAsString(objJson));
			System.out.println("--> response2: " + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objJson));
		}
	}
}