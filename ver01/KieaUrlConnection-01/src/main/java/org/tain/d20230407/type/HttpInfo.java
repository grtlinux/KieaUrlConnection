package org.tain.d20230407.type;

import java.util.Map;

import lombok.Data;

@Data
public class HttpInfo {

	/*
	 * https://example.com:8080/
	 * 
	 * protocol : http, https, ftp ...
	 * domain   : example.com ...
	 * port     : 8080
	 * path     : /docs, /v1/job/{id}
	 * query    : val1=123&val2=foo&val3=bar
	 * parameter -> GET.query or POST.json
	 */
	private String url;
	
	private String path = "";
	
	private String method;
	
	private Map<String,String> header;
	
	private Map<String,String> params;
	
	/*
	 * response
	 */
	private int statusCode;
	
	private String message;
	
	private StringBuffer response;
}
