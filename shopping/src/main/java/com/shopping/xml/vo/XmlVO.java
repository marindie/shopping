package com.shopping.xml.vo;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ROOT")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlVO {
	
	@XmlElement(name="KEY")
	private String key;
	@XmlElement(name="VALUE")
	private String id;
	@XmlElement(name="EXPAND")
	private Object expand;
	@XmlElement(name="MAP")
	private Map<String, Object> map;
	@XmlElement(name="LIST")
	private List<Object> list;

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Object getExpand() {
		return expand;
	}
	public void setExpand(Object expand) {
		this.expand = expand;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}

}
