package com.campaign.model;

import java.io.Serializable;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Response implements Serializable  {

	private static final long serialVersionUID = 2852195346089635434L;
	private int statusCode;
	private String statusMessage;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)	
	private Map<String,String> errorMap;
	
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public Map<String, String> getErrorMap() {
		return errorMap;
	}
	public void setErrorMap(Map<String, String> errorMap) {
		this.errorMap = errorMap;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
}
