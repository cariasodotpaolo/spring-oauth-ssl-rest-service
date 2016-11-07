package com.shipserv.hr.response;

import java.util.ArrayList;
import java.util.List;

import org.test.model.TestMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

//@XmlRootElement(name="messageRoot")
public class MessageResponse {
	
	@JsonProperty("testMessage")
	private List<TestMessage> testMessages;
	
	public MessageResponse() {
		// TODO Auto-generated constructor stub
		testMessages = new ArrayList<TestMessage>();
	}

	
	public List<TestMessage> getTestMessages() {
		return testMessages;
	}

	public void setTestMessages(List<TestMessage> testMessages) {
		this.testMessages = testMessages;
	}

}
