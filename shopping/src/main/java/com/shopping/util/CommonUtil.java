package com.shopping.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	/**
	 * Gets the class and method name.
	 * 현재 로직의 클래스명, 메소드명을 가져온다.
	 * @return the class and method name
	 */
	public static String[] getClassAndMethodName() {
 
		StackTraceElement e[] = Thread.currentThread().getStackTrace();
		int level = 2;
		String[] nameArr = new String[2];
 
		if(e != null && e.length >= level) {
			StackTraceElement s = e[level];
			if(s != null) {
				String[] arr = s.getClassName().split("[.]");
				String classNm = arr[arr.length - 1];
				String methodNm = s.getMethodName();
 
				nameArr[0] = classNm;
				nameArr[1] = methodNm;
				return nameArr;
			}
		}
 
		nameArr[0] = "-";
		nameArr[1] = "-";
		return nameArr;
	}
 
	/**
	 * Gets the form txt.
	 *
	 * @param request the request
	 *
	 * @return the form txt
	 */
	public Map<String,Object> getFormTxt(HttpServletRequest request){
		Map<String,Object> paramMaps = new HashMap<String, Object>();
 
		Enumeration top_ename = request.getParameterNames();
		String paramName = "";
		String paramValue = "";
 
		logger.info("================================== Parameter ==================================");
		logger.info("== URI : "+request.getRequestURI());
		logger.info("== method : "+request.getMethod());
		while(top_ename.hasMoreElements()){
			paramName = (String)top_ename.nextElement();
			paramValue = request.getParameter(paramName);
 
			if( paramName.indexOf("PW")>-1) {
//				logger.info("== "+paramName+" : " + getStringEncrypt(paramValue));
			} else {
				logger.info("== "+paramName+" : " + paramValue);
			}
 
			if(paramValue.equals("[]")) {
				paramValue = paramValue.replace("[]", "");
			}
 
			paramValue = paramValue.replace("<", "&lt;");
			paramValue = paramValue.replace(">", "&gt;");
 
			paramMaps.put(paramName, paramValue);
		}
		logger.info("===============================================================================");
 
		return paramMaps;
	}
	
	public List<Map<String, Object>> fromJsonToListMap(String jsonData){
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> data = null;
		try {
			data = mapper.readValue(jsonData, new TypeReference<List<Map<String, Object>>>(){});
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
		return data;
	}
	
	public boolean isEmpty(Object obj) {
		if(null == obj)
			return false;
		else if(obj instanceof String){
			if("".equals( obj.toString() ) ) {
				return true;
			}else {
				return false;
			}
		}
		return true;
	}
}
