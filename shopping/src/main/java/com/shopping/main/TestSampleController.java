package com.shopping.main;

import java.io.IOException;
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
}
