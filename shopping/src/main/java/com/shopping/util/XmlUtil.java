package com.shopping.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
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
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.shopping.xml.vo.XmlVO;

public class XmlUtil {
	
    public static void main(String[] args) {
        DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
        	docBuilder = docBuildFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element mainRootElement = doc.createElementNS("http://crunchify.com/CrunchifyCreateXMLDOM", "Companies");
            doc.appendChild(mainRootElement);
 
            // append child elements to root element
            mainRootElement.appendChild(getCompany(doc, "1", "Paypal", "Payment", "1000"));
            mainRootElement.appendChild(getCompany(doc, "2", "eBay", "Shopping", "2000"));
            mainRootElement.appendChild(getCompany(doc, "3", "Google", "Search", "3000"));
 
            // output DOM XML to console 
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
//            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            DOMSource source = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult console = new StreamResult(writer);
            transformer.transform(source, console);
//            System.out.println(writer.getBuffer().toString());
//            System.out.println(writer.getBuffer().toString().replaceAll("\n|\r", ""));
//            String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
 
            System.out.println("\nXML DOM Created Successfully..");
            
    		String json1 = "[\r\n" + 
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
            
            List<Map<String, Object>> a = JsonUtil.parseJsonGson(json1);            
            Map<String, Object> opt = getDefaultOptionList();
            System.out.println(a.toString());
            System.out.println(XmlUtil.parseXml(a,opt));
            
            XmlVO xmlVo = new XmlVO();
            xmlVo.setId("11");
            xmlVo.setKey("key1");
            
            XmlVO xmlVo1 = new XmlVO();
            xmlVo1.setId("22");
            xmlVo1.setKey("key2");
            
            Object obj1 = xmlVo1;
            List<Object> obj2 = new ArrayList<Object>();
            obj2.add(obj1);
            xmlVo.setList(obj2);
            opt.put("XMLVO",obj1);
            
            XmlVO xmlVo2 = new XmlVO();
            xmlVo2.setId("33");
            xmlVo2.setKey("key3");

            Object obj3 = xmlVo2;
            opt.put("XMLTemplateVO",obj3);
            obj2.add(obj3);
            xmlVo.setExpand(obj3);
            xmlVo.setMap(opt);
            
            System.out.println(XmlUtil.parseXml(xmlVo));
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }	
    
    private static Node getCompany(Document doc, String id, String name, String age, String role) {
        Element company = doc.createElement("Company");
        company.setAttribute("id", id);
        company.appendChild(getCompanyElements(doc, company, "Name", name));
        company.appendChild(getCompanyElements(doc, company, "Type", age));
        company.appendChild(getCompanyElements(doc, company, "Employees", role));
        return company;
    }
 
    // utility method to create text node
    private static Node getCompanyElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    
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
				rootElement = createNodes(doc,rootElement, (Map<String, Object>) listData.get(i));
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
}
