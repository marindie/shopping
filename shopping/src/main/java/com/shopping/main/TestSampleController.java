package com.shopping.main;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.service.MainService;
import com.shopping.util.CommonUtil;
import com.shopping.vo.MainVO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Handles requests for the application home page.
 */
@Controller
public class TestSampleController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestSampleController.class);
	@Autowired
	private MainService mainService;

	@RequestMapping(value = "/test", method = {RequestMethod.POST,RequestMethod.GET})
	public String test(Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Test Start");
		
		return "/sample/test";
	}
	
	@ResponseBody
	@RequestMapping(value = "/test/ajaxPostFormSample")
	public String ajaxPostFormSample(@RequestBody String formData) {
		logger.info(formData);
		return "SuccessForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "/test/ajaxXmlSample")
	public String ajaxXmlSample(@RequestBody String xml) {
		logger.info(xml);
		return "SuccessXml";
	}
	
	@ResponseBody
	@RequestMapping(value = "/test/ajaxPostFormJson")
	public String ajaxJsonSample(@RequestBody String json) {
		logger.info(json);
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> data = CommonUtil.parseJsonGson(json);
		logger.info(data.toString());
		
		return "SuccessJson";
	}
	
	@ResponseBody
	@RequestMapping(value = "/test/formtest")
	public String formTest(@RequestParam(value="param", required=true) List<String> params) {
		logger.info(params.toString());
		
		return "SuccessJson";
	}
	
	//HTTP Sample
	@ResponseBody
	@RequestMapping(value = "/test/url")
	public String urlSample(@RequestBody String data) {
		logger.info("url start");
		try {
			URL url = new URL("http://localhost:8080/main/test/ajaxPostFormSample");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST"); // 보내는 타입
			conn.setRequestProperty("Accept-Language", "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");
	
			// DATA
			String param = "{\"title\": \"asdasd\", \"body\" : \"ddddddddd\"}";
	
			// Request
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
			osw.write(param);
			osw.flush();
			
			// Response 
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			String line = null;
			while ((line = br.readLine()) != null) {
			logger.info(line);
			}

			// Close
			osw.close();
			br.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "SuccessUrl";
	}
	
	//Apache 
	@ResponseBody
	@RequestMapping(value = "/test/apache")
	public String apacheHttp(@RequestBody String params) {
		String SERVER_URL = "http://localhost:8080/main/test/ajaxPostFormSample";
		String responseData = "";
		
		HttpPost httpPost = new HttpPost(SERVER_URL);
		HttpEntity entity = null;
	 
		entity = new StringEntity(params, "UTF-8");
		
		httpPost.setEntity(entity);
		HttpClient httpClient = HttpClientBuilder.create().build();
	 
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HttpEntity responseEntity = httpResponse.getEntity();
		
		if (responseEntity != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				// From ByteArray to String
				responseEntity.writeTo(bos);
				responseData = bos.toString("UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (bos != null) {
						bos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseData;
	}
	
	//HttpClient to send a HTTP GET request to get the Google’s search result.
	@ResponseBody
	@RequestMapping(value = "/test/google", produces= {"text/plain;charset=euc-kr","application/xml;charset=euc-kr","application/json;charset=euc-kr"})
	public String googleSearch(@RequestBody String params) {
		String url = "http://www.google.com/search?q=httpClient";

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		StringBuffer result = new StringBuffer();
		String res = "";
		
		// add request header
//		httpGet.addHeader("User-Agent", "WONY");
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			System.out.println("Response Code : "+ response.getStatusLine().getStatusCode());
			System.out.println("Response Content : "+ response.getEntity().getContent());
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"euc-kr"));

			result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println("Response Data : "+ line);
				result.append(line);				
			}
//			res = new String(result.toString().getBytes(),"euc-kr");
			res = result.toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("RESULT : "+ res);
		return res;
	}
	
	//HttpClient to send an HTTP POST request to Apple.com search form. (Not Working)
	@ResponseBody
	@RequestMapping(value = "/test/apple")
	public String appleSearch(@RequestBody String param){
		String url = "https://selfsolve.apple.com/wcResults.do";

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		StringBuffer result = new StringBuffer();

		// add header
//		post.setHeader("User-Agent", USER_AGENT);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
		urlParameters.add(new BasicNameValuePair("cn", ""));
		urlParameters.add(new BasicNameValuePair("locale", ""));
		urlParameters.add(new BasicNameValuePair("caller", ""));
		urlParameters.add(new BasicNameValuePair("num", "12345"));

		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			HttpResponse httpResponse = httpClient.execute(post);
			System.out.println("Response Code : "+ httpResponse.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result.toString();
	}
}
