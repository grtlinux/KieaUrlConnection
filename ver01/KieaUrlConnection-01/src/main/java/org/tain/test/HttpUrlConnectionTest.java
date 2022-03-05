package org.tain.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUrlConnectionTest {

	private static final boolean flag = true;
	
	public static void main(String[] args) throws Exception {
		if (flag) test01();  // https://www.naver.com
		if (flag) test02();  // getHeaderFields
		if (flag) test03();  // GET
		if (flag) test04();  // POST
		if (flag) test05();  // POST
		if (flag) test06();  // json req/res
	}
	
	///////////////////////////////////////////////////////////////
	
	private static String url01 = "https://www.naver.com";
	
	private static void test01() throws Exception {
		URL url = new URL(url01);
		System.out.println("---> protocol: " + url.getProtocol());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setConnectTimeout(10 * 1000);  // millisec
		con.setReadTimeout(10 * 1000);
		con.setDefaultUseCaches(true);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestProperty("KEY", "VALUE");
		System.out.printf("---> (%d) %s\n", con.getResponseCode(), con.getResponseMessage());
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		String line;
		while ((line = reader.readLine()) != null) {
			System.out.println("\t" + line);
		}
		con.disconnect();
		reader.close();
	}
	
	private static void test02() throws Exception {
		URL url = new URL(url01);
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.setRequestMethod("GET");
		http.setRequestMethod("HEAD");
		
		System.out.printf("---> (%d) %s\n", http.getResponseCode(), http.getResponseMessage());
		System.out.println("---> " + http.getHeaderFields());
	}
	
	///////////////////////////////////////////////////////////////
	
	public static final String USER_AGENT = "Mozilla/5.0";

	private static void test03() throws Exception {
		String url = "http://bobr2.tistory.com/";

		URL obj = new URL(url);
		//URL obj = new URL(url01);
		System.out.println("---> protocol: " + obj.getProtocol());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//전송방식
		con.setRequestMethod("GET");
		//Request Header 정의
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setConnectTimeout(10000);	   //컨텍션타임아웃 10초
		con.setReadTimeout(5000);		   //컨텐츠조회 타임아웃 5총
		// parameters ?key1=val1&key2=val2
		con.setRequestProperty("key1", "val1");
		con.setRequestProperty("key2", "val2");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine).append("\n");
		}
		in.close();
		con.disconnect();
		
		//System.out.println("test3.조회결과 : " + response.toString());
	}

	private static void test04() throws Exception {
		String url = "https://selfsolve.apple.com/wcResults.do";
		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		
		URL obj = new URL(url);
		System.out.println("---> protocol: " + obj.getProtocol());
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setConnectTimeout(10000);	   //컨텍션타임아웃 10초
		con.setReadTimeout(5000);		   //컨텐츠조회 타임아웃 5총
		// Send post request
		con.setDoOutput(true);			  //항상 갱신된내용을 가져옴.
		con.setDoInput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
		
		// recv the result
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine).append('\n');
		}
		in.close();
		con.disconnect();
		
		System.out.println("test4.조회결과: " + response.toString());
	}

	private static void test05() throws Exception {
		String url = "http://bobr2.tistory.com/";
		String urlParameters = "?Param1=aaaa&Param2=bbbb";
		
		URL obj = new URL(url);
		System.out.println("---> protocol: " + obj.getProtocol());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setConnectTimeout(10000);	   //컨텍션타임아웃 10초
		con.setReadTimeout(5000);		   //컨텐츠조회 타임아웃 5총
		// Send post request
		con.setDoOutput(true);			  //항상 갱신된내용을 가져옴.
		con.setDoInput(true);			  //항상 갱신된내용을 가져옴.
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine).append('\n');
		}
		in.close();
		con.disconnect();
		
		System.out.println("test5.조회결과: " + response.toString());
	}

	///////////////////////////////////////////////////////////////
	
	private static void test06() throws Exception {
		String myUrl = "http://localhost:8080/v0.1/rest/custs";
		String jsonReq = "{\"id\":1,\"name\":\"Kang\",\"age\":35}";
		
		URL url = new URL(myUrl);
		System.out.println("---> protocol: " + url.getProtocol());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.setRequestMethod("POST");
		con.setConnectTimeout(10000);	   //컨텍션타임아웃 10초
		con.setReadTimeout(5000);		   //컨텐츠조회 타임아웃 5총
		con.setDoOutput(true);			  //항상 갱신된내용을 가져옴.
		con.setDoInput(true);			  //항상 갱신된내용을 가져옴.
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json; charset=utf-8");  // req json
		con.setRequestProperty("Content-Type", "application/json; utf-8");  // req json
		con.setRequestProperty("Accept", "application/json");  // res json
		
		// send req
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(jsonReq);
		wr.flush();
		wr.close();
		System.out.println("test6.send: " + jsonReq);
		
		// recv res
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine).append('\n');
		}
		in.close();
		con.disconnect();
		
		System.out.println("test6.recv: " + response.toString());
	}
}
