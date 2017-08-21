package com.shopping.vo;

import java.sql.Date;

public class BoardVO {
	private Integer num; 
	private String name; 
	private String title; 
	private String content; 
	private Integer readCount; 
	private Date writeDate;
	/**
	 * @return the num
	 */
	public Integer getNum() {
		return num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(Integer num) {
		this.num = num;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the readCount
	 */
	public Integer getReadCount() {
		return readCount;
	}
	/**
	 * @param readCount the readCount to set
	 */
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	/**
	 * @return the writeDate
	 */
	public Date getWriteDate() {
		return writeDate;
	}
	/**
	 * @param writeDate the writeDate to set
	 */
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
}
