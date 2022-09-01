package org.tain.httpclient;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HttpClient {

	public JsonNode get(JsonNode reqJsonNode) throws Exception {
		return get(reqJsonNode, false);
	}
	
	public JsonNode get(JsonNode reqJsonNode, boolean flagAccessToken) throws Exception {
//		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
//		
//		if (Flag.flag) {
//			log.info("================== START: {} ===================", lnsJson.getName());
//			log.info(">>>>> REQ.httpUrl(method): {} ({})", lnsJson.getHttpUrl(), lnsJson.getHttpMethod());
//		}
		
		if (Boolean.TRUE) {
			String httpUrl = "http://example.com/"; //lnsJson.getHttpUrl();
			HttpMethod httpMethod = HttpMethod.GET;
			
			String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(reqJsonNode);
			//log.info(">>>>> REQ.lnsJson        = {}", json);
			
			Map<String,String> reqMap = new ObjectMapper().readValue("{}" /* lnsJson.getReqJsonData()*/, new TypeReference<Map<String,String>>(){});
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.setAll(reqMap);
			
			UriComponents builder = UriComponentsBuilder.fromHttpUrl(httpUrl)
					.queryParams(map)
					.build(true);
			httpUrl = builder.toString();
			//log.info(">>>>> REQ.httpUrl(method) = {} ({})", httpUrl, httpMethod);
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.setContentType(MediaType.APPLICATION_JSON);
			//log.info(">>>>> REQ.reqHeaders     = {}", reqHeaders);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(reqHeaders);
			//log.info(">>>>> REQ.reqHttpEntity  = {}", reqHttpEntity);
			
			ResponseEntity<String> response = null;
			try {
				response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
						httpUrl
						, httpMethod
						, reqHttpEntity
						, String.class);
				
				//log.info(">>>>> RES.getStatusCodeValue() = {}", response.getStatusCodeValue());
				//log.info(">>>>> RES.getStatusCode()      = {}", response.getStatusCode());
				//log.info(">>>>> RES.getBody()            = {}", response.getBody());
				json = response.getBody();
				//lnsJson = new ObjectMapper().readValue(json, LnsJson.class);
				//log.info(">>>>> RES.lnsJson              = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			} catch (Exception e) {
				//e.printStackTrace();
				String message = e.getMessage();
				//log.error("ERROR >>>>> {}", message);
				int pos1 = message.indexOf('[');
				int pos2 = message.lastIndexOf(']');
				//lnsJson.setCode("99999");
				//lnsJson.setStatus("FAIL");
				//lnsJson.setMsgJson(message.substring(pos1 + 1, pos2));
			}
		}
		
		if (Boolean.TRUE) {
			//log.info("================== FINISH: {} ===================", lnsJson.getName());
		}
		
		JsonNode resJsonNode = null;
		return resJsonNode;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public JsonNode post(JsonNode reqJsonNode) throws Exception {
		return post(reqJsonNode, false);
	}
	
	public JsonNode post(JsonNode reqJsonNode, boolean flagAccessToken) throws Exception {
//		log.info("KANG-20200721 >>>>> {} {}", CurrentInfo.get());
//		
//		if (Flag.flag) {
//			log.info("================== START: {} ===================", lnsJson.getName());
//			log.info(">>>>> REQ.httpUrl(method): {} ({})", lnsJson.getHttpUrl(), lnsJson.getHttpMethod());
//		}
		
		JsonNode resJsonNode = null;
		
		if (Boolean.TRUE) {
			String httpUrl = "http://example.com/"; //lnsJson.getHttpUrl();
			HttpMethod httpMethod = HttpMethod.POST;
			
			//String json = JsonPrint.getInstance().toPrettyJson(lnsJson);
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode headNode = null;
			JsonNode bodyNode = null;
			String json = null;
			if (Boolean.TRUE) {
				// del header
				JsonNode node = objectMapper.readTree("{}" /* reqJsonNode.getReqJsonData() */);
				//headNode = node.at("/__head_data");
				//bodyNode = node.at("/__body_data");
				//json = bodyNode.toPrettyString();
			}
			//log.info(">>>>> REQ.lnsJson        = {}", json);
			
			HttpHeaders reqHeaders = new HttpHeaders();
			reqHeaders.setContentType(MediaType.APPLICATION_JSON);
			//log.info(">>>>> REQ.reqHeaders     = {}", reqHeaders);
			
			HttpEntity<String> reqHttpEntity = new HttpEntity<>(json, reqHeaders);
			//log.info(">>>>> REQ.reqHttpEntity  = {}", reqHttpEntity);
			
			ResponseEntity<String> response = null;
			try {
				response = RestTemplateConfig.get(RestTemplateType.SETENV).exchange(
						httpUrl
						, httpMethod
						, reqHttpEntity
						, String.class);
				
				//log.info(">>>>> RES.getStatusCodeValue() = {}", response.getStatusCodeValue());
				//log.info(">>>>> RES.getStatusCode()      = {}", response.getStatusCode());
				//log.info(">>>>> RES.getBody()            = {}", response.getBody());
				if (Boolean.TRUE) {
					// add header
					ObjectNode node = (ObjectNode) headNode;
					node.put("reqres", "0710");
					//node.put("resTime", LnsNodeTools.getTime());
					node.put("resCode", "000");
					node.put("resMessage", "SUCCESS");
					
					resJsonNode = (JsonNode) objectMapper.createObjectNode();
					((ObjectNode) resJsonNode).set("__head_data", node);
					((ObjectNode) resJsonNode).set("__body_data", objectMapper.readTree(response.getBody()));
					json = resJsonNode.toPrettyString();
				}
				//reqJsonNode.setResJsonData(json);
				//log.info(">>>>> RES.lnsJson              = {}", JsonPrint.getInstance().toPrettyJson(lnsJson));
			} catch (Exception e) {
				//e.printStackTrace();
				String message = e.getMessage();
				//log.error("ERROR >>>>> {}", message);
				//int pos1 = message.indexOf('[');
				//int pos2 = message.lastIndexOf(']');
				//lnsJson.setCode("99999");
				//lnsJson.setStatus("FAIL");
				//lnsJson.setMsgJson(message.substring(pos1 + 1, pos2));
			}
		}
		
		if (Boolean.TRUE) {
			//log.info("================== FINISH: {} ===================", lnsJson.getName());
		}
		
		return resJsonNode;
	}
}
