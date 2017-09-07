package com.shopping.util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

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

public class JsonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	/**
	 * Take Json String. Check if it's JsonObject or JsonArray. Parse Json into List Map and Return
	 * @param String json
	 * @return List<Map<String, Object>> listData
	 * @author Seung Won Chung
	 */
	public static List<Map<String, Object>> parseJsonToList(String json) {
		List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;
		try {
			rootNode = mapper.readValue(json, JsonNode.class);
			if(rootNode.isArray()){
				listData = parseJsonArrayType1(json);
			}else {
				dataMap = parseJsonObject(json);	
				listData.add(dataMap);
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
		return listData;
	}
	
	/**
	 * Take Json String. Check if it's JsonObject or JsonArray. Parse Json into List Map. Convert into String and Return.
	 * @param String json
	 * @return String jsonStr
	 * @author Seung Won Chung
	 */	
	public static String parseJsonToString(String json) {
		String jsonStr = "";
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode;
		try {
			rootNode = mapper.readValue(json, JsonNode.class);
			if(rootNode.isArray()){
				jsonStr = JsonUtil.toString(parseJsonArrayType1(json));
			}else {
				jsonStr = JsonUtil.toString(parseJsonObject(json));	
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
		return jsonStr;
	}	
	
	/**
	 * Take Json String. Parse Json into Map and Return.
	 * @param String json
	 * @return Map<String, Object> jsonMapData
	 * @author Seung Won Chung
	 */	
	public static Map<String, Object> parseJsonObject(String json){
		Map<String, Object> jsonMapData = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		MapType typeMap = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class);
		try {
			jsonMapData = mapper.readValue(json, typeMap);
			printMapData(jsonMapData, "parseJsonObject START", "parseJsonObject END");
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
		return jsonMapData;
	}

	/**
	 * Take Json String. Parse Json into List Map and Return. Use TypeReferece
	 * @param String json
	 * @return List<Map<String, Object>> jsonListMapData
	 * @author Seung Won Chung
	 */
	public static List<Map<String, Object>> parseJsonArrayType2(String json) {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> jsonListMapData = new ArrayList<Map<String, Object>>();
		try {
			jsonListMapData = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
			printMapData(jsonListMapData, "parseJsonListMap(TypeReference) START", "parseJsonListMap(TypeReference) END");
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
		return jsonListMapData;
	}
	
	/**
	 * Take Json String. Parse Json into List Map and Return. Use TypeFactory
	 * @param String json
	 * @return List<Map<String, Object>> jsonListMapData
	 * @author Seung Won Chung
	 */
	public static List<Map<String, Object>> parseJsonArrayType1(String json) {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> jsonListMapData = new ArrayList<Map<String, Object>>();
		try {
			MapType typeMap = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class);
			CollectionType typeList = TypeFactory.defaultInstance().constructCollectionType(List.class, typeMap);
			jsonListMapData = mapper.readValue(json, typeList);
			printMapData(jsonListMapData, "parseJsonListMap(TypeFactory) START", "parseJsonListMap(TypeFactory) END");
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
		return jsonListMapData;
	}
	
	/**
	 * Take Json String. Parse Json into List Map. Convert into xml String and Return.
	 * @param String json
	 * @return String xml
	 * @author Seung Won Chung
	 */
	public static String convertJsonToXml(String json) {
		String xml = "";
		XmlMapper xmlMapper = new XmlMapper();
	    try {
	    	xml = xmlMapper.writeValueAsString(parseJsonToList(json));
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
		return xml;
	}
	
	/**
	 * Take List<Map<String, Object>> jsonList. Convert into xml String and Return.
	 * @param List<Map<String, Object>> jsonList
	 * @return String xml
	 * @author Seung Won Chung
	 */
	public static String convertJsonToXml(List<Map<String, Object>> jsonList) {
		String xml = "";
		XmlMapper xmlMapper = new XmlMapper();
	    try {
	    	xml = xmlMapper.writeValueAsString(jsonList);
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
		return xml;
	}	
	
	/**
	 * Take List<Map<String, Object>> jsonList. Convert into json String and Return.
	 * @param List<Map<String, Object>> jsonList
	 * @return String jsonStr
	 * @author Seung Won Chung
	 */
	public static String toString(List<Map<String, Object>> jsonList) {
		String jsonStr = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(jsonList);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	/**
	 * Take Map<String, Object> jsonMap. Convert into json String and Return.
	 * @param Map<String, Object> jsonMap
	 * @return String jsonStr
	 * @author Seung Won Chung
	 */
	public static String toString(Map<String, Object> jsonMap) {
		String jsonStr = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonStr = mapper.writeValueAsString(jsonMap);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}	
	
	/**
	 * Take Map<String, Object> jsonMap. Print Data
	 * @param Map<String, Object> jsonMap
	 * @return void
	 * @author Seung Won Chung
	 */
	public static void printMapData(Map<String, Object> jsonMap,String start, String end) {
		logger.info("===================== "+ start + " ===================");
		SortedSet<String> keys = new TreeSet<String>(jsonMap.keySet());
		ObjectMapper mapper = new ObjectMapper();
		for(String key : keys) {
			if(JsonUtil.isNotEmpty(jsonMap.get(key))) {
				try {
					JsonNode jsonNode = mapper.readValue(jsonMap.get(key).toString(), JsonNode.class);
					if(jsonNode.isArray()) {
						
					}else if(jsonNode.isObject()) {
						printMapData(parseJsonObject(jsonMap.get(key).toString()),key+" START",key+" END");
					}else if(jsonNode.isTextual()) {
						logger.info("key : value : type = " + key + " : "+(JsonUtil.isEmpty(jsonMap.get(key)) ? "" : jsonMap.get(key).toString()) + " : " +  (JsonUtil.isEmpty(jsonMap.get(key)) ? "" : jsonMap.get(key).getClass().getName()));			
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
			}else {
				logger.info("key : value : type = " + key + " : "+(JsonUtil.isEmpty(jsonMap.get(key)) ? "" : jsonMap.get(key).toString()) + " : " +  (JsonUtil.isEmpty(jsonMap.get(key)) ? "" : jsonMap.get(key).getClass().getName()));				
			}
		}
		logger.info("===================== "+ end + " ===================");
	}
	
	/**
	 * Take Map<String, Object> jsonMap. Print Data
	 * @param Map<String, Object> jsonMap
	 * @return void
	 * @author Seung Won Chung
	 */
	public static void printMapData(List<Map<String, Object>> jsonListMap,String start, String end) {
		logger.info("===================== "+ start + " ===================");
		for(int i = 0 ; i < jsonListMap.size() ; i++) {
			SortedSet<String> keys = new TreeSet<String>(jsonListMap.get(i).keySet());
			for(String key : keys) {
				logger.info("key : value : type = " + key + " : "+(JsonUtil.isEmpty(jsonListMap.get(i).get(key)) ? "" : jsonListMap.get(i).get(key).toString()) + " : " +  (JsonUtil.isEmpty(jsonListMap.get(i).get(key)) ? "" : jsonListMap.get(i).get(key).getClass().getName()));
			}			
		}
		logger.info("===================== "+ end + " ===================");
	}
	
	/**
	 * Check if it is empty
	 * @param Object
	 * @return boolean
	 * @author Seung Won Chung
	 */
    public static boolean isEmpty(Object obj){
        if( obj instanceof String ) return obj==null || "".equals(obj.toString().trim());
        else if( obj instanceof List ) return obj==null || ((List)obj).isEmpty();
        else if( obj instanceof Map ) return obj==null || ((Map)obj).isEmpty();
        else if( obj instanceof Object[] ) return obj==null || Array.getLength(obj)==0;
        else return obj==null;
    }

	/**
	 * Check if it is empty. return opposite value
	 * @param Object
	 * @return boolean
	 * @author Seung Won Chung
	 */
    public static boolean isNotEmpty(Object s){
        return !isEmpty(s);
    }
	
	public static void main(String[] args) {
		String json = "{\"menu\": {\r\n" + 
				"    \"header\": \"SVG Viewer\",\r\n" + 
				"    \"items\": [\r\n" + 
				"        {\"id\": \"Open\"},\r\n" + 
				"        {\"id\": \"OpenNew\", \"label\": \"Open New\"},\r\n" + 
				"        null,\r\n" + 
				"        {\"id\": \"ZoomIn\", \"label\": \"Zoom In\"},\r\n" + 
				"        {\"id\": \"ZoomOut\", \"label\": \"Zoom Out\"},\r\n" + 
				"        {\"id\": \"OriginalView\", \"label\": \"Original View\"},\r\n" + 
				"        null,\r\n" + 
				"        {\"id\": \"Quality\"},\r\n" + 
				"        {\"id\": \"Pause\"},\r\n" + 
				"        {\"id\": \"Mute\"},\r\n" + 
				"        null,\r\n" + 
				"        {\"id\": \"Find\", \"label\": \"Find...\"},\r\n" + 
				"        {\"id\": \"FindAgain\", \"label\": \"Find Again\"},\r\n" + 
				"        {\"id\": \"Copy\"},\r\n" + 
				"        {\"id\": \"CopyAgain\", \"label\": \"Copy Again\"},\r\n" + 
				"        {\"id\": \"CopySVG\", \"label\": \"Copy SVG\"},\r\n" + 
				"        {\"id\": \"ViewSVG\", \"label\": \"View SVG\"},\r\n" + 
				"        {\"id\": \"ViewSource\", \"label\": \"View Source\"},\r\n" + 
				"        {\"id\": \"SaveAs\", \"label\": \"Save As\"},\r\n" + 
				"        null,\r\n" + 
				"        {\"id\": \"Help\"},\r\n" + 
				"        {\"id\": \"About\", \"label\": \"About Adobe CVG Viewer...\"}\r\n" + 
				"    ]\r\n" + 
				"}}";
		
		String jsonArray = "[\r\n" + 
				"	{\r\n" + 
				"		\"color\": \"red\",\r\n" + 
				"		\"value\": \"#f00\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		\"color\": \"green\",\r\n" + 
				"		\"value\": \"#0f0\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		\"color\": \"blue\",\r\n" + 
				"		\"value\": \"#00f\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		\"color\": \"cyan\",\r\n" + 
				"		\"value\": \"#0ff\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		\"color\": \"magenta\",\r\n" + 
				"		\"value\": \"#f0f\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		\"color\": \"yellow\",\r\n" + 
				"		\"value\": \"#ff0\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		\"color\": \"black\",\r\n" + 
				"		\"value\": \"#000\"\r\n" + 
				"	}\r\n" + 
				"]";
		JsonUtil.parseJsonToList(json);
		JsonUtil.parseJsonToList(jsonArray);	
	}
}
