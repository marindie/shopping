package com.shopping.util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

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
	
	public static void printMapData(Map<String, Object> map,String start, String end) {
		logger.info("===================== "+ start + " ===================");
		SortedSet<String> keys = new TreeSet<String>(map.keySet());
		for(String key : keys) {
			logger.info("key : value : type = " + key + " : "+(isEmpty(map.get(key)) ? "" : map.get(key).toString()) + " : " +  (isEmpty(map.get(key)) ? "" : map.get(key).getClass().getName()));				
		}
		logger.info("===================== "+ end + " ===================");
	}
	
	public static void printMapData(List<Map<String, Object>> listMap,String start, String end) {
		logger.info("===================== "+ start + " ===================");
		for(int i = 0 ; i < listMap.size() ; i++) {
			SortedSet<String> keys = new TreeSet<String>(listMap.get(i).keySet());
			for(String key : keys) {
				logger.info("key : value : type = " + key + " : "+(isEmpty(listMap.get(i).get(key)) ? "" : listMap.get(i).get(key).toString()) + " : " +  (isEmpty(listMap.get(i).get(key)) ? "" : listMap.get(i).get(key).getClass().getName()));
			}			
		}
		logger.info("===================== "+ end + " ===================");
	}
	
    public static boolean isEmpty(Object obj){
        if( obj instanceof String ) return obj==null || "".equals(obj.toString().trim());
        else if( obj instanceof List ) return obj==null || ((List)obj).isEmpty();
        else if( obj instanceof Map ) return obj==null || ((Map)obj).isEmpty();
        else if( obj instanceof Object[] ) return obj==null || Array.getLength(obj)==0;
        else return obj==null;
    }

    public static boolean isNotEmpty(Object s){
        return !isEmpty(s);
    }

}
