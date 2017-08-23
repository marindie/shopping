package com.shopping.main;

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

import com.shopping.service.MainService;
import com.shopping.vo.MainVO;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value ="/test")
public class TestSampleController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestSampleController.class);
	@Autowired
	private MainService mainService;

	@RequestMapping(value = "/", method = {RequestMethod.POST,RequestMethod.GET})
	public String test(Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Test Start");
		
		return "/sample/test";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajaxPostFormSample")
	public String ajaxPostFormSample(@RequestBody String xml) {
		logger.info(xml);
		return "Success";
	}
	
	@ResponseBody
	@RequestMapping(value = "/ajaxXmlSample")
	public String ajaxXmlSample(@RequestBody String xml) {
		return "Success";
	}

}
