package org.tain.monhttpclient;

import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.tain.utils.CurrentInfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonHttpClient {

	private final ObjectMapper objectMapper = new ObjectMapper();
	
	//////////////////////////////////////////////////////////////////////
	public ObjectNode execute(ObjectNode trans) throws Exception {
		log.info("KANG-20210320 >>>>> {} {}", CurrentInfo.get());
		log.info("=========================== MonHttpClient.execute START =============================");
		
		if (Boolean.TRUE) {
			//JsonNode info = trans.getMonJsonNode("_info");
			String method = trans.get("method").asText().toUpperCase();
			switch (method) {
			case "GET":
				trans = get(trans, method);
				break;
			case "POST":
			case "PUT":
			case "DELETE":
				trans = post(trans, method);
				break;
			default:
				break;
			}
		}
		
		log.info("=========================== MonHttpClient.execute END =============================");
		
		return trans;
	}
	
	//////////////////////////////////////////////////////////////////////
	private ObjectNode get(ObjectNode trans, String method) throws Exception {
		log.info("KANG-20210320 >>>>> {} {}", CurrentInfo.get(), method);
		
		//JsonNode resJsonNode = null;
		
		MultiValueMap<String, String> mapParam = null;
		if (Boolean.TRUE) {
			@SuppressWarnings("unchecked")
			Map<String, Object> mapRequest = objectMapper.treeToValue(trans.get("request"), Map.class);
			
			mapParam = new LinkedMultiValueMap<>();
			//mapParam.setAll(mapRequest);
			for (Map.Entry<String, Object> entry : mapRequest.entrySet()) {
				//System.out.println(">>> " + entry.getKey() + ":" + String.valueOf(entry.getValue()));
				mapParam.add(entry.getKey(), URLEncoder.encode(String.valueOf(entry.getValue()), "utf-8"));
			}
			log.info("1 >>>>> {}-mapParam = {}", method, mapParam);
		}
		
		String httpUrl = null;
		if (Boolean.TRUE) {
			httpUrl = trans.get("httpUrl").asText();
			//httpUrl += trans.get("path").asText();
			//httpUrl += trans.get("version").asText();
			httpUrl += trans.get("resource").asText();
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(httpUrl)
					.queryParams(mapParam)
					.build(true);
			httpUrl = builder.toString();
			log.info("2 >>>>> {}-httpUrl = {}", method, httpUrl);
		}
		
		HttpMethod httpMethod = null;
		if (Boolean.TRUE) {
			httpMethod = HttpMethod.valueOf(method);
			log.info("3 >>>>> {}-httpMethod = {} in {}", method, httpMethod, HttpMethod.values());
		}
		
		HttpHeaders reqHeaders = null;
		if (Boolean.TRUE) {
			reqHeaders = new HttpHeaders();
			//reqHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			//reqHeaders.setContentType(MediaType.APPLICATION_JSON);
			
			@SuppressWarnings("unchecked")
			Map<String, String> mapHeaders = objectMapper.treeToValue(trans.get("headers"), Map.class);
			for (Map.Entry<String, String> header : mapHeaders.entrySet()) {
				reqHeaders.set(header.getKey(), header.getValue());
			}
			
			log.info("4 >>>>> {}-reqHeaders = {}", method, reqHeaders);
		}
		
		HttpEntity<String> reqHttpEntity = null;
		if (Boolean.TRUE) {
			reqHttpEntity = new HttpEntity<>(reqHeaders);
			log.info("5 >>>>> {}-reqHttpEntity  = {}", method, reqHttpEntity);
		}
		
		ResponseEntity<String> response = null;
		try {
			response = getCustomRestTemplate().exchange(
					httpUrl
					, httpMethod
					, reqHttpEntity
					, String.class);
			
			String responseBody = response.getBody();
			
			log.info("6 >>>>> {}-getStatusCodeValue() = {}", method, response.getStatusCodeValue());
			log.info("6 >>>>> {}-getStatusCode()      = {}", method, response.getStatusCode());
			log.info("6 >>>>> {}-getBody()            = {}", method, responseBody);
			
			trans.set("response", objectMapper.readTree(responseBody));
			//log.info("7 >>>>> {}-trans (RES)          = {}", method, trans.toPrettyString());
			
			//if (Flag.flag) throw new Exception("template error");
			
			//trans.put("/_info", "returnCode", "00000");
			//trans.put("/_info", "returnMessage", "SUCCESS");
			//trans.put("/_info", "statusCodeValue", response.getStatusCodeValue());
			//trans.put("/_info", "statusCode", String.valueOf(response.getStatusCode()));
			//trans.put("_response", new MonJsonNode(responseBody != null ? responseBody : "{}"));
		} catch (Exception e) {
			//e.printStackTrace();
			String message = e.getMessage();
			log.error("9 ERROR >>>>> {}", message);
			//int pos1 = message.indexOf('[');
			//int pos2 = message.lastIndexOf(']');
			//trans.put("/_info", "returnCode", "99999");
			//trans.put("/_info", "returnMessage", "ERROR");
			//trans.put("/_info", "errorMessage", message);
		}
		
		return trans;
	}
	
	//////////////////////////////////////////////////////////////////////
	private ObjectNode post(ObjectNode trans, String method) throws Exception {
		log.info("KANG-20210320 >>>>> {} {}", CurrentInfo.get());
		
		String httpUrl = null;
		if (Boolean.TRUE) {
			httpUrl = trans.get("httpUrl").asText();
			//httpUrl += trans.get("path").asText();
			//httpUrl += trans.get("version").asText();
			httpUrl += trans.get("resource").asText();
			log.info("1 >>>>> {}-httpUrl = {}", method, httpUrl);
		}
		
		HttpMethod httpMethod = null;
		if (Boolean.TRUE) {
			httpMethod = HttpMethod.valueOf(method);
			log.info("2 >>>>> {}-httpMethod = {}", method, httpMethod);
		}
		
		HttpHeaders reqHeaders = null;
		if (Boolean.TRUE) {
			reqHeaders = new HttpHeaders();
			//reqHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			//reqHeaders.setContentType(MediaType.APPLICATION_JSON);
			
			@SuppressWarnings("unchecked")
			Map<String, String> mapHeaders = objectMapper.treeToValue(trans.get("headers"), Map.class);
			for (Map.Entry<String, String> header : mapHeaders.entrySet()) {
				reqHeaders.set(header.getKey(), header.getValue());
			}
			
			log.info("3 >>>>> {}-reqHeaders = {}", method, reqHeaders);
		}
		
		HttpEntity<String> reqHttpEntity = null;
		if (Boolean.TRUE) {
			JsonNode request = trans.get("request");
			reqHttpEntity = new HttpEntity<>(request != null ? request.toPrettyString() : null, reqHeaders);
			log.info("4 >>>>> {}-reqHttpEntity  = {}", method, reqHttpEntity);
		}
		
		ResponseEntity<String> response = null;
		try {
			response = getCustomRestTemplate().exchange(
					httpUrl
					, httpMethod
					, reqHttpEntity
					, String.class);
			
			String responseBody = response.getBody();
			
			log.info("5 >>>>> {}-getStatusCodeValue() = {}", method, response.getStatusCodeValue());
			log.info("5 >>>>> {}-getStatusCode()      = {}", method, response.getStatusCode());
			log.info("5 >>>>> {}-getBody()            = {}", method, responseBody);
			
			trans.set("response", objectMapper.readTree(responseBody));
			//log.info("6 >>>>> {}-trans (RES)          = {}", method, trans.toPrettyString());
			
			//if (Flag.flag) throw new Exception("template error");
			
			//trans.put("/_info", "returnCode", "00000");
			//trans.put("/_info", "returnMessage", "SUCCESS");
			//trans.put("/_info", "statusCodeValue", response.getStatusCodeValue());
			//trans.put("/_info", "statusCode", String.valueOf(response.getStatusCode()));
			//trans.put("_response", new MonJsonNode(responseBody != null ? responseBody : "{}"));
		} catch (Exception e) {
			//e.printStackTrace();
			String message = e.getMessage();
			log.error("9 ERROR >>>>> {}", message);
			//int pos1 = message.indexOf('[');
			//int pos2 = message.lastIndexOf(']');
			//trans.put("/_info", "returnCode", "99999");
			//trans.put("/_info", "returnMessage", "ERROR");
			//trans.put("/_info", "errorMessage", message);
		}
		return trans;
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	private RestTemplate getCustomRestTemplate() {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectTimeout(10 * 1000);
		httpRequestFactory.setReadTimeout(60 * 1000);
		HttpClient httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(200)
				.setMaxConnPerRoute(20)
				.build();
		httpRequestFactory.setHttpClient(httpClient);
		return new RestTemplate(httpRequestFactory);
	}
}
