package org.tain.monhttpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MonHttpClientTestMain {

	public static void main(String[] args) throws Exception {
		System.out.println(">>>>> main <<<<<");
		
		if (!Boolean.TRUE) test01();
		if (Boolean.TRUE) test02();
	}
	
	/////////////////////////////////////////////////////////////////////
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	/////////////////////////////////////////////////////////////////////
	private static void test01() throws Exception {
		System.out.println("-----------  test01 : GET 001 -----------");
		
		ObjectNode trans = objectMapper.createObjectNode();
		
		// URL, method
		trans.put("httpUrl", "http://localhost:8000");
		trans.put("path", "/");  // path
		trans.put("version", "v1");  // version
		trans.put("resource", "/deposit/invr_dpsa/account_list");  // resource
		trans.put("method", "GET");
		
		// Headers
		ObjectNode headers = objectMapper.createObjectNode();
		//headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Content-Type", "application/json");
		trans.set("headers", headers);
		
		// requests
		ObjectNode request = objectMapper.createObjectNode();
		//request.put("fld1", 123);
		//request.put("fld2", "안녕! Hi");  // URLEncoder.encode("", "utf-8");
		request.put("before_inquiry_trace_info", "ABCDE123456789");
		trans.set("request", request);
		
		System.out.println(">>> REQ: " + trans.toPrettyString());
		
		MonHttpClient monHttpClient = new MonHttpClient();
		trans = monHttpClient.execute(trans);
		
		System.out.println(">>> RES: " + trans.toPrettyString());
	}

	/////////////////////////////////////////////////////////////////////
	private static void test02() throws Exception {
		System.out.println("-----------  test02 : POST 004 -----------");
		
		ObjectNode trans = objectMapper.createObjectNode();
		
		// URL, method
		trans.put("httpUrl", "http://localhost:8000");
		trans.put("path", "/");  // path
		trans.put("version", "v1");  // version
		trans.put("resource", "/deposit/invr_dpsa/transfer/withdraw");  // resource
		trans.put("method", "POST");
		
		// Headers
		ObjectNode headers = objectMapper.createObjectNode();
		//headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Content-Type", "application/json");
		trans.set("headers", headers);
		
		// requests
		ObjectNode request = objectMapper.createObjectNode();
		request.put("account_num", "ABCD1234");
		request.put("currency_code", "KRW");
		request.put("tran_amt", 123000);
		request.put("withdraw_reason_code", "A1");
		request.put("settlement_info_withdraw_cnt", 2);
		
		ArrayNode arrNode1 = objectMapper.createArrayNode();
		ObjectNode objNode1 = objectMapper.createObjectNode();
		objNode1.put("bank_code", "B1");
		objNode1.put("settlement_account_num", "BBBB2222");
		objNode1.put("settlement_amt", 300.00);
		arrNode1.add(objNode1);
		ObjectNode objNode2 = objectMapper.createObjectNode();
		objNode2.put("bank_code", "C1");
		objNode2.put("settlement_account_num", "DDDD1111");
		objNode2.put("settlement_amt", 200.00);
		arrNode1.add(objNode2);
		request.set("settlement_info_withdraw", arrNode1);
		
		request.put("consignment_guarantee", 123.45);
		request.put("deposit_requirement_change_details_cnt", 2);
		
		ArrayNode arrNode2 = objectMapper.createArrayNode();
		ObjectNode objNode3 = objectMapper.createObjectNode();
		objNode3.put("deposit_requirement_type", "B2");
		objNode3.put("increase_amt", 123.12);
		objNode3.put("decrease_amt", 123.00);
		arrNode2.add(objNode3);
		ObjectNode objNode4 = objectMapper.createObjectNode();
		objNode4.put("deposit_requirement_type", "C2");
		objNode4.put("increase_amt", 111.12);
		objNode4.put("decrease_amt", 222.00);
		arrNode2.add(objNode4);
		request.set("deposit_requirement_change_details", arrNode2);
		
		request.put("optr_email_yn", "Y");
		request.put("optr_email_address", "4455qqq@naver.com");
		request.put("apvr_email_yn", "Y");
		request.put("apvr_email_address", "4455qqq@naver.com");
		request.put("optr_sms_yn", "Y");
		request.put("optr_mobile_num", "01054149634");
		request.put("apvr_sms_yn", "Y");
		request.put("apvr_mobile_num", "01054149634");
		trans.set("request", request);
		
		System.out.println(">>> REQ: " + trans.toPrettyString());
		
		MonHttpClient monHttpClient = new MonHttpClient();
		trans = monHttpClient.execute(trans);
		
		System.out.println(">>> RES: " + trans.toPrettyString());
	}
}
