package com.shopping.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.service.MainService;
import com.shopping.vo.MainVO;

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
		try {
			List<Map<String, Object>> data = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
			
			logger.info(data.toString());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "SuccessJson";
	}
	
	@ResponseBody
	@RequestMapping(value = "/test/url")
	public String urlSample(@RequestBody String data) {
		logger.info("url start");
		// 연결
		try {

			URL url = new URL("http://localhost:8080/main/test/ajaxPostFormSample");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST"); // 보내는 타입
			conn.setRequestProperty("Accept-Language", "ko-kr,ko;q=0.8,en-us;q=0.5,en;q=0.3");
	
			// 데이터
			String param = "{\"title\": \"asdasd\", \"body\" : \"ddddddddd\"}";
	
			// 전송
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
			osw.write(param);
			osw.flush();
			// 응답
			BufferedReader br = null;
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

			String line = null;
			while ((line = br.readLine()) != null) {
			logger.info(line);
			}

			// 닫기
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
}
