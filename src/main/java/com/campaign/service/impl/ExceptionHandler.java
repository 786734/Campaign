package com.campaign.service.impl;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.campaign.model.Response;

@Component
public class ExceptionHandler {

	public Response throwBadRequest(Map<String, String> error) {
		
		Response response = new Response();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setStatusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
		response.setErrorMap(error);
		return response;
	}

}
