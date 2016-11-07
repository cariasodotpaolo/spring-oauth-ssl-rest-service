package org.test.model;

import javax.xml.bind.annotation.XmlRootElement;


public class TestMessage {
	
	
	private String message;
	
	private String name;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
