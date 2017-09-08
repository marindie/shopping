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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import com.fasterxml.jackson.xml.XmlMapper;

public class JsonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper mapper = new ObjectMapper().configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		
	/**
	 * Take Json String. Check if it's JsonObject or JsonArray. Parse Json into List Map and Return
	 * @param String json
	 * @return List<Map<String, Object>> listData
	 * @author Seung Won Chung
	 */
	public static List<Map<String, Object>> parseJson(String json) {
		List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
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
	 * Take Json String. Parse Json into Map and Return.
	 * @param String json
	 * @return Map<String, Object> jsonMapData
	 * @author Seung Won Chung
	 */	
	public static Map<String, Object> parseJsonObject(String json){
		Map<String, Object> jsonMapData = new HashMap<String, Object>();
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
	    	xml = xmlMapper.writeValueAsString(parseJson(json));
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
		try {
			SimpleModule simpleModule = new SimpleModule("emptyFilter", new Version(1, 0, 0, null, null, null));
			simpleModule.addSerializer(Map.class, new JsonSerializer<Map>() {  
			    @Override
			    public void serialize(Map map, JsonGenerator jp, SerializerProvider sp) throws IOException, JsonProcessingException {
			        jp.writeStartObject();

			        for (Object key : map.keySet()) {
			            if (!"".equals((String)map.get(key)) && map.get(key) != null) {
			                jp.writeStringField((String)key, (String)map.get(key));
			            }
			        }
			        jp.writeEndObject();
			    }
			});
			mapper.registerModule(simpleModule);
//			mapper.setSerializationInclusion(Include.NON_EMPTY); 
//			mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false); 
			jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonList);
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
		try {
			SimpleModule simpleModule = new SimpleModule("emptyFilter", new Version(1, 0, 0, null, null, null));
			simpleModule.addSerializer(Map.class, new JsonSerializer<Map>() {  
			    @Override
			    public void serialize(Map map, JsonGenerator jp, SerializerProvider sp) throws IOException, JsonProcessingException {
			        jp.writeStartObject();
			        for (Object key : map.keySet()) {
			            if (!"".equals((String)map.get(key)) && map.get(key) != null) {
			                jp.writeStringField((String)key, (String)map.get(key));
			            }
			        }
			        jp.writeEndObject();
			    }
			});	
			mapper.registerModule(simpleModule);
//			mapper.setSerializationInclusion(Include.NON_EMPTY); 
//			mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false); 
			jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);
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
		for(String key : keys) {
			logger.info("key : value : type = " + key + " : "+(JsonUtil.isEmpty(jsonMap.get(key)) ? "" : jsonMap.get(key).toString()) + " : " +  (JsonUtil.isEmpty(jsonMap.get(key)) ? "" : jsonMap.get(key).getClass().getName()));				
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
	
    //테스트 결과 사용하는데 있어서 제한사항
    //1. String 을 Map 또는 List 로 변환하는것은 무난해 보임.
    //2. writeValueAsString 를 사용해서 String으로 변환시 안에 Array 가 있으면 안됨. 각 내용물을 하나하나 또 변환해서 String으로 처리해야함.
    //3. 이와 같은 이유로 일단 Map 이나 List 로 변환후, 해당 Array 를 추출해서 List<Map<String, Object>> 로 Casting 하는게 편함.
    //4. null 또는 "" 케이스 빼는게 가능하다해서 모쥴 남이 샘플로 만들어 쓰는거 사용해서 처리함.
    //5. JsonGenerator 로 하나 하나 추가하는 방식도 가능함.
    //6. Annotation 방식도 존재 하는데 Model 방식으로는 아직 필요해 보이지 않아서 샘플은 없음. (http://tutorials.jenkov.com/java-json/jackson-annotations.html) 참고
//	public static void main(String[] args) {
//		String json1 = "{\"menu\": {\r\n" + 
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
//		String json2 = "{\r\n" + 
//				"	\"id\": \"0001\",\r\n" + 
//				"	\"type\": \"donut\",\r\n" + 
//				"	\"name\": \"Cake\",\r\n" + 
//				"	\"image\":\r\n" + 
//				"		{\r\n" + 
//				"			\"url\": \"images/0001.jpg\",\r\n" + 
//				"			\"width\": 200,\r\n" + 
//				"			\"height\": 200\r\n" + 
//				"		},\r\n" + 
//				"	\"thumbnail\":\r\n" + 
//				"		{\r\n" + 
//				"			\"url\": \"images/thumbnails/0001.jpg\",\r\n" + 
//				"			\"width\": 32,\r\n" + 
//				"			\"height\": 32\r\n" + 
//				"		}\r\n" + 
//				"}";
//		
//		String jsonArray = "[\r\n" + 
//				"	{\r\n" + 
//				"		color: \"red\",\r\n" + 
//				"		value: \"#f00\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		color: \"green\",\r\n" + 
//				"		value: \"#0f0\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		color: \"blue\",\r\n" + 
//				"		value: \"#00f\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		color: \"cyan\",\r\n" + 
//				"		value: \"#0ff\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		color: \"magenta\",\r\n" + 
//				"		value: \"#f0f\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		color: \"yellow\",\r\n" + 
//				"		value: \"#ff0\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		color: \"black\",\r\n" + 
//				"		value: \"#000\"\r\n" + 
//				"	},\r\n" + 
//				"	{\r\n" + 
//				"		color: \"\",\r\n" + 
//				"		value: null\r\n" + 
//				"	}\r\n" + 
//				"]";
//		
//		Map<String, Object> res = JsonUtil.parseJson(json1).get(0);
//		System.out.println(res.toString());	
//		Map<String, Object> res1 = (Map<String, Object>) res.get("menu");
//		System.out.println(res1.toString());
//		List<Map<String, Object>> res2 = (List<Map<String, Object>>) res1.get("items");
//		for(int i = 0 ; i < res2.size(); i++) {
//			if(isNotEmpty(res2.get(i)))
//				System.out.println(res2.get(i).toString());
//		}		
//		System.out.println(JsonUtil.parseJson(json2).get(0).toString());
//		System.out.println(JsonUtil.parseJson(jsonArray).toString());
//		System.out.println(JsonUtil.toString(JsonUtil.parseJson(jsonArray)));
//	}
}
