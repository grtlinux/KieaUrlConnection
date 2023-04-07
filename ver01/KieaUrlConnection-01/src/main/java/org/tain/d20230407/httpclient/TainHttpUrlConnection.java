package org.tain.d20230407.httpclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tain.d20230407.type.HttpInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TainHttpUrlConnection {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	public HttpInfo connect(HttpInfo info) {
		
		try {
			URL url = new URL(info.getUrl());
			
			// protocol
			String protocol = url.getProtocol();
			
			// method
			String method = info.getMethod();
			
			System.out.println(">>> " + protocol + ", " + method);
			
			switch (protocol) {
			case "http":
				switch (method) {
				case "GET":
					info = httpGet(info);
					break;
				case "POST":
					info = httpPost(info);
					break;
				default:
					System.out.println("ERROR: method error ?? " + method);
				}
				break;
			case "https":
				switch (method) {
				case "GET":
					info = httpsGet(info);
					break;
				case "POST":
					info = httpsPost(info);
					break;
				default:
					System.out.println("ERROR: method error ?? " + method);
				}
				break;
			default:
				System.out.println("ERROR: protocol error ?? " + protocol);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("finally ...");
		}
		
		return info;
	}
	
	/////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////
	private HttpInfo httpGet(HttpInfo info) throws Exception {
		
		MultiValueMap<String, String> mapParams = new LinkedMultiValueMap<>();
		mapParams.setAll(info.getParams());
		
		// params -> query
		UriComponents uriComponents = UriComponentsBuilder
				.fromHttpUrl(info.getUrl() + info.getPath())
				.queryParams(mapParams)
				.build(true);
		String strUrl = uriComponents.toString();
		
		URL url = new URL(strUrl);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		
		// method
		urlConnection.setRequestMethod(info.getMethod());
		
		// header
		for (Map.Entry<String, String> entry : info.getHeader().entrySet()) {
			System.out.printf("%s:%s%n", entry.getKey(), entry.getValue());
			urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
		}
		
		// get url
		System.out.println(">>> " + urlConnection.toString());
		
		// connect
		urlConnection.connect();
		
		int statusCode = urlConnection.getResponseCode();
		String message = urlConnection.getResponseMessage();
		System.out.println(">>> " + statusCode + ": " + message);
		
		info.setStatusCode(statusCode);
		info.setMessage(message);
		
		// recv
		if (statusCode == 200) {
			// in, success
			if (Boolean.TRUE) {
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String line;
				StringBuffer sb = new StringBuffer();
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
				in.close();
				
				info.setResponse(sb);
			}
		} else {
			// fail
			info.setResponse(null);
		}
		
		return info;
	}
	
	/////////////////////////////////////////////////////////////////////
	private HttpInfo httpPost(HttpInfo info) throws Exception {
		
		URL url = new URL(info.getUrl() + info.getPath());
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		
		// method
		urlConnection.setRequestMethod(info.getMethod());
		
		// header
		for (Map.Entry<String, String> entry : info.getHeader().entrySet()) {
			System.out.printf("%s:%s%n", entry.getKey(), entry.getValue());
			urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
		}
		
		// get url
		System.out.println(">>> " + urlConnection.toString());
		
		// connect
		urlConnection.setDoOutput(true);
		urlConnection.connect();
		
		// send
		if (Boolean.TRUE) {
			ObjectNode objectNode = objectMapper.createObjectNode();
			for (Map.Entry<String, String> entry : info.getParams().entrySet()) {
				objectNode.put(entry.getKey(), entry.getValue());
			}
			
			OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
			out.write(objectNode.toString());
			out.close();
		}
		
		int statusCode = urlConnection.getResponseCode();
		String message = urlConnection.getResponseMessage();
		System.out.println(">>> " + statusCode + ": " + message);
		
		info.setStatusCode(statusCode);
		info.setMessage(message);
		
		// recv
		if (statusCode == 200) {
			// success
			if (Boolean.TRUE) {
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String line;
				StringBuffer sb = new StringBuffer();
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
				in.close();
				
				info.setResponse(sb);
			}
			
		} else {
			// fail
			info.setResponse(null);
		}
		
		return info;
	}
	
	/////////////////////////////////////////////////////////////////////
	private HttpInfo httpsGet(HttpInfo info) throws Exception {
		
		MultiValueMap<String, String> mapParams = new LinkedMultiValueMap<>();
		mapParams.setAll(info.getParams());
		
		// params -> query
		UriComponents uriComponents = UriComponentsBuilder
				.fromHttpUrl(info.getUrl() + info.getPath())
				.queryParams(mapParams)
				.build(true);
		String strUrl = uriComponents.toString();
		
		URL url = new URL(strUrl);
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
		
		// method
		urlConnection.setRequestMethod(info.getMethod());
		
		// header
		for (Map.Entry<String, String> entry : info.getHeader().entrySet()) {
			System.out.printf("%s:%s%n", entry.getKey(), entry.getValue());
			urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
		}
		
		// get url
		System.out.println(">>> " + urlConnection.toString());
		
		// connect
		urlConnection.connect();
		
		int statusCode = urlConnection.getResponseCode();
		String message = urlConnection.getResponseMessage();
		System.out.println(">>> " + statusCode + ": " + message);
		
		info.setStatusCode(statusCode);
		info.setMessage(message);
		
		// recv
		if (statusCode == 200) {
			// in, success
			if (Boolean.TRUE) {
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String line;
				StringBuffer sb = new StringBuffer();
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
				in.close();
				
				info.setResponse(sb);
			}
		} else {
			// fail
			info.setResponse(null);
		}
		
		return info;
	}
	
	/////////////////////////////////////////////////////////////////////
	private HttpInfo httpsPost(HttpInfo info) throws Exception {
		
		URL url = new URL(info.getUrl() + info.getPath());
		HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
		
		// method
		urlConnection.setRequestMethod(info.getMethod());
		
		// header
		for (Map.Entry<String, String> entry : info.getHeader().entrySet()) {
			System.out.printf("%s:%s%n", entry.getKey(), entry.getValue());
			urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
		}
		
		// get url
		System.out.println(">>> " + urlConnection.toString());
		
		// connect
		urlConnection.setDoOutput(true);
		urlConnection.connect();
		
		// send
		if (Boolean.TRUE) {
			ObjectNode objectNode = objectMapper.createObjectNode();
			for (Map.Entry<String, String> entry : info.getParams().entrySet()) {
				objectNode.put(entry.getKey(), entry.getValue());
			}
			
			OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
			out.write(objectNode.toString());
			out.close();
		}
		
		int statusCode = urlConnection.getResponseCode();
		String message = urlConnection.getResponseMessage();
		System.out.println(">>> " + statusCode + ": " + message);
		
		info.setStatusCode(statusCode);
		info.setMessage(message);
		
		// recv
		if (statusCode == 200) {
			// success
			if (Boolean.TRUE) {
				BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				String line;
				StringBuffer sb = new StringBuffer();
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
				in.close();
				
				info.setResponse(sb);
			}
			
		} else {
			// fail
			info.setResponse(null);
		}
		
		return info;
	}
}
