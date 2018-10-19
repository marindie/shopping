package com.shopping.util;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class CommonUtil {
	public static int INDENT_FACTOR = 4;
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	private static ObjectMapper mapper = new ObjectMapper().configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	
	//===========================
	// JSON
	//===========================
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
    
	//===========================
	// XML
	//===========================    
    public static String parseXml(List<Map<String, Object>> listData, Map<String, Object> option) {
    	Document doc;
    	Transformer transformer;
    	StringWriter writer = new StringWriter();
    	String retStr = null;    	
    	
    	try {
    		doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element rootElement = doc.createElement("ROOT");
			doc.appendChild(rootElement);
			for(int i = 0 ; i < listData.size(); i++) {
				Element items = doc.createElement("ITEMS");
				items = createNodes(doc,items, (Map<String, Object>) listData.get(i));
				rootElement.appendChild(items);
			}			
			transformer = TransformerFactory.newInstance().newTransformer();
			if(CommonUtil.isNotEmpty(option.get("OMIT_XML_DECLARATION")))
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, option.get("OMIT_XML_DECLARATION").toString());
			if(CommonUtil.isNotEmpty(option.get("INDENT")))
				transformer.setOutputProperty(OutputKeys.INDENT, option.get("INDENT").toString());			
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			retStr = writer.getBuffer().toString();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
    	return retStr;
    }
    
    public static String parseXml(Object obj) {
    	StringWriter sw = new StringWriter();
	    try {
		    JAXBContext jc = JAXBContext.newInstance(obj.getClass());		    
		    Marshaller marshaller = jc.createMarshaller();
		    marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8"); 
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "");
	        marshaller.marshal(obj, sw);
		} catch (JAXBException e) {
		    e.printStackTrace();
		}
	    return sw.toString();
    }
    
    public static Element createNodes(Document doc, Element element, Map<String, Object> map) {
		SortedSet<String> keys = new TreeSet<String>(map.keySet());
		for(String key : keys) {
			if(CommonUtil.isEmpty(map.get(key))) {
				element.appendChild(addNode(doc,key,""));
			}else {
				if(map.get(key).toString().matches("^\\[.*")) {
					Element newElement = doc.createElement(key);
					List<Map<String, Object>> listMap = (List<Map<String, Object>>) map.get(key);				
					for(int i = 0 ; i < listMap.size(); i++) {
						if(CommonUtil.isNotEmpty(listMap.get(i))){
							newElement = createNodes(doc,newElement, (Map<String, Object>) listMap.get(i));
							element.appendChild(newElement);						
						}
					}				
				}else if(map.get(key).toString().matches("^\\{.*")) {
					Element newElement = doc.createElement(key);
					newElement = createNodes(doc,newElement, (Map<String, Object>) map.get(key));
					element.appendChild(newElement);
				}else {
					if(CommonUtil.isEmpty(map.get(key))) {
						element.appendChild(addNode(doc,key,""));
					}else {
						element.appendChild(addNode(doc,key,map.get(key).toString()));
					}				
				}				
			}
		}
    	return element;
    }
    
    public static Node addNode(Document doc, String name, String value) {
    	Element node = doc.createElement(name);
    	node.appendChild(doc.createTextNode(value));
    	return node;
    }
    
    public static Map<String, Object> getDefaultOptionList() {
    	Map<String, Object> option = new HashMap<String, Object>();
    	option.put("OMIT_XML_DECLARATION", "no");
    	option.put("INDENT", "yes");
    	return option;
    }
    
    public static String convertXMLToJson(Object xml) {
    	if(xml instanceof String) {
    		JSONObject jsonObject = XML.toJSONObject(xml.toString());
        	return jsonObject.toString(INDENT_FACTOR);
    	}else if(xml instanceof List) {
    		JSONObject jsonObject = XML.toJSONObject(parseXml((List<Map<String, Object>>) xml,getDefaultOptionList()));
        	return jsonObject.toString(INDENT_FACTOR);
    	}else {
    		JSONObject jsonObject = XML.toJSONObject(parseXml(xml));
    		return jsonObject.toString(INDENT_FACTOR);
    	}
    }    
    
	//===========================
	// ETC
	//===========================
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
