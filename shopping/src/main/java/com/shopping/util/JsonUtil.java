package com.shopping.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper mapper = new ObjectMapper().configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		
	public static List<Map<String, Object>> parseJsonJackson(String json) {
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
		
	public static Map<String, Object> parseJsonObject(String json){
		Map<String, Object> jsonMapData = new HashMap<String, Object>();
		MapType typeMap = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class);
		try {
			jsonMapData = mapper.readValue(json, typeMap);
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

	public static List<Map<String, Object>> parseJsonArrayType2(String json) {
		List<Map<String, Object>> jsonListMapData = new ArrayList<Map<String, Object>>();		
		try {
			jsonListMapData = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
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
	
	public static List<Map<String, Object>> parseJsonArrayType1(String json) {
		List<Map<String, Object>> jsonListMapData = new ArrayList<Map<String, Object>>();		
		try {
			MapType typeMap = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class);
			CollectionType typeList = TypeFactory.defaultInstance().constructCollectionType(List.class, typeMap);
			jsonListMapData = mapper.readValue(json, typeList);
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
	
	public static String convertJsonToXml(String json) {
		String xml = "";
//		XmlFactory factory = new XmlFactory(new WstxInputFactory(), new WstxOutputFactory());
		ObjectMapper xmlMapper = new ObjectMapper();
	    try {
	    	xml = xmlMapper.writeValueAsString(parseJsonJackson(json));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}
	
	public static String convertJsonToXml(List<Map<String, Object>> jsonList) {
		String xml = "";
//		XmlFactory factory = new XmlFactory(new WstxInputFactory(), new WstxOutputFactory());
		ObjectMapper xmlMapper = new ObjectMapper();
//    	xmlMapper.getFactory().getXMLOutputFactory().setProperty("javax.xml.stream.isRepairingNamespaces", true);
	    try {
	    	xml = xmlMapper.writeValueAsString(jsonList);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}	
	
//	public static String toString(List<Map<String, Object>> jsonList) {
//		String jsonStr = "";
//		try {
//			SimpleModule simpleModule = new SimpleModule("emptyFilter", new Version(1, 0, 0, null, null, null));
//			simpleModule.addSerializer(Map.class, new JsonSerializer<Map>() {  
//			    @Override
//			    public void serialize(Map map, JsonGenerator jp, SerializerProvider sp) throws IOException, JsonProcessingException {
//			        jp.writeStartObject();
//
//			        for (Object key : map.keySet()) {
//			            if (!"".equals((String)map.get(key)) && map.get(key) != null) {
//			                jp.writeStringField((String)key, (String)map.get(key));
//			            }
//			        }
//			        jp.writeEndObject();
//			    }
//			});
//			mapper.registerModule(simpleModule);
//			mapper.setSerializationInclusion(Include.NON_EMPTY); 
//			mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
//			jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonList);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		return jsonStr;
//	}
	
//	public static String toString(Map<String, Object> jsonMap) {
//		String jsonStr = "";
//		try {
//			SimpleModule simpleModule = new SimpleModule("emptyFilter", new Version(1, 0, 0, null, null, null));
//			simpleModule.addSerializer(Map.class, new JsonSerializer<Map>() {  
//			    @Override
//			    public void serialize(Map map, JsonGenerator jp, SerializerProvider sp) throws IOException, JsonProcessingException {
//			        jp.writeStartObject();
//			        for (Object key : map.keySet()) {
//			            if (!"".equals((String)map.get(key)) && map.get(key) != null) {
//			                jp.writeStringField((String)key, (String)map.get(key));
//			            }
//			        }
//			        jp.writeEndObject();
//			    }
//			});	
//			mapper.registerModule(simpleModule);
//			mapper.setSerializationInclusion(Include.NON_EMPTY); 
//			mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false); 
//			jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return jsonStr;
//	}	
	    
    public static List<Map<String, Object>> parseJsonGson(String json){
    	JsonParser p = new JsonParser();
        JsonElement jsonElement = p.parse(json);
        List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>(); 
        if (jsonElement.isJsonArray()) {
        	listData = new Gson().fromJson(json,new TypeToken<List<Map<String, Object>>>(){}.getType());
        } else if (jsonElement.isJsonObject()) {
        	listData.add((Map<String, Object>) new Gson().fromJson(json,new TypeToken<Map<String, Object>>(){}.getType()));
        } else {
            throw new RuntimeException("Unexpected JSON type: " + json.getClass());
        }
        return listData;
    }
	
    //테스트 결과후 Summary
    //========================== Jackson =================
    //1. String 을 Map 또는 List 로 변환하는것은 무난해 보임.
    //2. writeValueAsString 를 사용해서 String으로 변환시 안에 Array 가 있으면 안됨. 각 내용물을 하나하나 또 변환해서 String으로 처리해야함.
    //3. 이와 같은 이유로 일단 Map 이나 List 로 변환후, 해당 Array 를 추출해서 List<Map<String, Object>> 로 Casting 하는게 편함.
    //4. null 또는 "" 케이스 빼는게 가능하다해서 남이 샘플로 만들어 쓰는거 사용해서 처리함.
    //5. JsonGenerator 로 하나 하나 추가하는 방식도 가능함.
    //6. Annotation 방식도 존재 하는데 Model 방식으로는 아직 필요해 보이지 않아서 샘플은 없음. (http://tutorials.jenkov.com/java-json/jackson-annotations.html) 참고
    //========================== Jackson =================
    
    //========================== Gson =================
    //특별한 문제 없이 List<Map<String, Object>> 및 String 변환이 됨. Array 인지 Object 인지만 판단하면 되는 정도.
    //========================== Gson =================
	public static void main(String[] args) {
		String json1 = "{\"menu\": {\r\n" + 
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
		
		String json2 = "{\r\n" + 
				"	\"id\": \"0001\",\r\n" + 
				"	\"type\": \"donut\",\r\n" + 
				"	\"name\": \"Cake\",\r\n" + 
				"	\"image\":\r\n" + 
				"		{\r\n" + 
				"			\"url\": \"images/0001.jpg\",\r\n" + 
				"			\"width\": 200,\r\n" + 
				"			\"height\": 200\r\n" + 
				"		},\r\n" + 
				"	\"thumbnail\":\r\n" + 
				"		{\r\n" + 
				"			\"url\": \"images/thumbnails/0001.jpg\",\r\n" + 
				"			\"width\": 32,\r\n" + 
				"			\"height\": 32\r\n" + 
				"		}\r\n" + 
				"}";
		
		String jsonArray = "[\r\n" + 
				"	{\r\n" + 
				"		color: \"red\",\r\n" + 
				"		value: \"#f00\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		color: \"green\",\r\n" + 
				"		value: \"#0f0\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		color: \"blue\",\r\n" + 
				"		value: \"#00f\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		color: \"cyan\",\r\n" + 
				"		value: \"#0ff\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		color: \"magenta\",\r\n" + 
				"		value: \"#f0f\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		color: \"yellow\",\r\n" + 
				"		value: \"#ff0\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		color: \"black\",\r\n" + 
				"		value: \"#000\"\r\n" + 
				"	},\r\n" + 
				"	{\r\n" + 
				"		color: \"\",\r\n" + 
				"		value: null\r\n" + 
				"	}\r\n" + 
				"]";
		
//		String xml = "<?xml version='1.0'?><person><firstName>Homer</firstName><lastName>"
//		           + "Simpson</lastName><address><street>10 Main Street</street><city>"
//		           + "Springfield</city><country>US</country></address></person>";
//
//		ObjectMapper xmlMapper = new ObjectMapper();
//		ObjectMapper jsonMapper = new ObjectMapper();
//		try {
//			JsonNode node = xmlMapper.readTree(xml.getBytes());
//			String json = jsonMapper.writeValueAsString(node);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		List<Map<String, Object>> res = JsonUtil.parseJsonJackson(json1);
//		System.out.println(res.toString());
//		System.out.println(JsonUtil.toString(res));
		List<Map<String, Object>> res = JsonUtil.parseJsonGson(json2);
		System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(res));
		
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
//		System.out.println(JsonUtil.convertJsonToXml(JsonUtil.parseJson(json1)));
//		System.out.println(JsonUtil.convertJsonToXml(JsonUtil.parseJson(jsonArray)));
		
		
//		XMLStreamWriter xmlWriter;
//		try {
//			xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(System.out);
//			xmlWriter.writeStartDocument();
//			xmlWriter.writeStartElement("bar");
//			xmlWriter.writeEndElement();
//			xmlWriter.writeStartElement("foo"); // Exception with jackson >= 2.8.0 (woodstox-core 5.x)
//			xmlWriter.writeEndElement();
//			xmlWriter.writeEndDocument();
//			xmlWriter.flush();
//		} catch (XMLStreamException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FactoryConfigurationError e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
