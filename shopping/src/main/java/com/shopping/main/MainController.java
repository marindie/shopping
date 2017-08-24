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
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private MainService mainService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView main(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		return new ModelAndView("main","command",new MainVO());
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String checkUser(@ModelAttribute MainVO mainVO, Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("id ="+mainVO.getId().toString());
		logger.info("pwd ="+mainVO.getPwd());
		
		model.addAttribute("data", mainService.selectCustomer(mainVO));
		
		return "login";
	}
	
	@RequestMapping(value= "selectAllCust", method = {RequestMethod.POST,RequestMethod.GET})
	public String selectAllCust(@ModelAttribute MainVO mainVO, Model model) {
		List<Map<String, Object>> result = mainService.selectAllCust();
//		logger.info(result.toString());
		for(int i = 0 ; i < result.size() ; i++) {
			Map<String, Object> data = result.get(i);
			for(String key : data.keySet()) {
				logger.info(key +" :: "+ data.get(key));
			}
		}
		model.addAttribute("listData", result);
		return "sample/test";
	}
	
	@ResponseBody
	@RequestMapping(value= "ajaxStringSample", method = {RequestMethod.POST,RequestMethod.GET})
	public String ajaxSample(@RequestBody String data) {
		logger.info(data);
		return "ajax response data";
	}
}
