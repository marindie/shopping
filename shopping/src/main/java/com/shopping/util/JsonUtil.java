package com.shopping.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.xml.XmlMapper;
import com.shopping.dao.BoardDAO;

public class JsonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	public static List<Map<String, Object>> parseJson(String json) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;
		try {
			rootNode = mapper.readValue(json, JsonNode.class);
			if(rootNode.isArray()){
				dataList = parseJsonArrayType1(json);
			}else {
				dataMap = parseJsonObject(json);	
				dataList.add(dataMap);
			}
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
		return dataList;
	}
	
	public static Map<String, Object> parseJsonObject(String json){
		logger.info("======== parseJsonObject using TypeReference Start =========");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		MapType typeMap = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class);
		try {
			dataMap = mapper.readValue(json, typeMap);
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
		logger.info("======== parseJsonObject using TypeReference End =========");
		return dataMap;
	}

	public static List<Map<String, Object>> parseJsonArrayType2(String json) {
		logger.info("======== parseJsonArray using TypeReference Start =========");
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> resultListMap = new ArrayList<Map<String, Object>>();
		try {
			resultListMap = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
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
		logger.info("======== parseJsonArray using TypeReference End =========");
		return resultListMap;
	}
	
	public static List<Map<String, Object>> parseJsonArrayType1(String json) {
		logger.info("======== parseJson using TypeFactory Start =========");
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> resultListMap = new ArrayList<Map<String, Object>>();
		try {
			MapType typeMap = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class);
			CollectionType typeList = TypeFactory.defaultInstance().constructCollectionType(List.class, typeMap);
			resultListMap = mapper.readValue(json, typeList);
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
		logger.info("======== parseJson using TypeFactory End =========");
		return resultListMap;
	}
	
	public static String convertJsonToXml(String json) {
		String retStr = "";
		XmlMapper xmlMapper = new XmlMapper();
	    try {
	    	retStr = xmlMapper.writeValueAsString(parseJson(json));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.codehaus.jackson.map.JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retStr;
	}
	
	public static String convertJsonToXml(List<Map<String, Object>> jsonList) {
		String retStr = "";
		XmlMapper xmlMapper = new XmlMapper();
	    try {
	    	retStr = xmlMapper.writeValueAsString(jsonList);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.codehaus.jackson.map.JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retStr;
	}	
	
	public static String getJsonString(List<Map<String, Object>> jsonList) {
		String retStr = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			retStr = mapper.writeValueAsString(jsonList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retStr;
	}
	
//	public static void main(String[] args) {
//		String json = "{\"menu\": {\r\n" + 
//				"    \"header\": \"SVG Viewer\",\r\n" + 
//				"    \"items\": [\r\n" + 
//				"        {\"id\": \"Open\"},\r\n" + 
//				"        {\"id\": \"OpenNew\", \"label\": \"Open New\"},\r\n" + 
//				"        null,\r\n" + 
//				"        {\"id\": \"ZoomIn\", \"label\": \"Zoom In\"},\r\n" + 
//				"        {\"id\": \"ZoomOut\", \"label\": \"Zoom Out\"},\r\n" + 
//				"        {\"id\": \"OriginalView\", \"label\": \"Original View\"},\r\n" + 
//				"        null,\r\n" + 
//				"        {\"id\": \"Quality\"},\r\n" + 
//				"        {\"id\": \"Pause\"},\r\n" + 
//				"        {\"id\": \"Mute\"},\r\n" + 
//				"        null,\r\n" + 
//				"        {\"id\": \"Find\", \"label\": \"Find...\"},\r\n" + 
//				"        {\"id\": \"FindAgain\", \"label\": \"Find Again\"},\r\n" + 
//				"        {\"id\": \"Copy\"},\r\n" + 
//				"        {\"id\": \"CopyAgain\", \"label\": \"Copy Again\"},\r\n" + 
//				"        {\"id\": \"CopySVG\", \"label\": \"Copy SVG\"},\r\n" + 
//				"        {\"id\": \"ViewSVG\", \"label\": \"View SVG\"},\r\n" + 
//				"        {\"id\": \"ViewSource\", \"label\": \"View Source\"},\r\n" + 
//				"        {\"id\": \"SaveAs\", \"label\": \"Save As\"},\r\n" + 
//				"        null,\r\n" + 
//				"        {\"id\": \"Help\"},\r\n" + 
//				"        {\"id\": \"About\", \"label\": \"About Adobe CVG Viewer...\"}\r\n" + 
//				"    ]\r\n" + 
//				"}}";
//		
//		String jsonArray = "[\r\n" + 
//				"	{\r\n" + 
//				"		\"color\": \"red\",\r\n" + 
//				"		\"value\": \"#f00\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		\"color\": \"green\",\r\n" + 
//				"		\"value\": \"#0f0\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		\"color\": \"blue\",\r\n" + 
//				"		\"value\": \"#00f\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		\"color\": \"cyan\",\r\n" + 
//				"		\"value\": \"#0ff\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		\"color\": \"magenta\",\r\n" + 
//				"		\"value\": \"#f0f\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		\"color\": \"yellow\",\r\n" + 
//				"		\"value\": \"#ff0\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		\"color\": \"black\",\r\n" + 
//				"		\"value\": \"#000\"\r\n" + 
//				"	}\r\n" + 
//				"]";
//		JsonUtil.parseJson(json);
//		JsonUtil.parseJson(jsonArray);	
//	}
}
