package com.campaign.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.campaign.model.PostRequest;

@Component
public class Validator {

	public Map<String, String> validateAddCampaignFieldValues(PostRequest request) {
		Map<String,String> map = new HashMap<String, String>();
		LocalDate effectiveDate = null;
		LocalDate endDate = null;
		
		asserNotNull(Constants.CLIENT_NAME,request.getClientName(),map);
		asserNotNull(Constants.CAMPAIGN_NAME,request.getCampaignName(),map);
		
		boolean isEffectiveDateNull = asserNotNull(Constants.EFFECTIVE_DATE,request.getEffectiveDate(),map);
		
		if(!isEffectiveDateNull)
			effectiveDate = validateDateFormate(Constants.EFFECTIVE_DATE,request.getEffectiveDate(),map);
		
		boolean isEndDateNull = asserNotNull(Constants.END_DATE,request.getEndDate(),map);
		if(!isEndDateNull)
			endDate = validateDateFormate(Constants.END_DATE,request.getEndDate(),map);
		
		if(effectiveDate !=  null && endDate != null) {
			//JAVA 8 Feature
			if(effectiveDate.isAfter(endDate))
				map.put(Constants.EFFECTIVE_DATE, Constants.DATE_RANGE_ERROR);
		}
		return  map;
	}


	private boolean asserNotNull(String field, String fieldValue, Map<String, String> error) {
		boolean isNull = false;
		if(StringUtils.isEmpty(fieldValue)) {
			error.put(field, field +" should not empty");
			isNull = true;
		}
		return isNull;
	}
	
	private LocalDate validateDateFormate(String field, String fieldValue, Map<String, String> error) {
		
		LocalDate date = null;
		try {
			//JAVA 8 Feature
			date = LocalDate.parse(fieldValue, Constants.DATE_FORMAT);
		}catch (DateTimeParseException e) {
			error.put(field, Constants.DATE_FORMAT_ERROR);
		}
		return date;
	}


	public Map<String, String> validateUpdateCampaignFieldValues(PostRequest request) {
		Map<String,String> map = new HashMap<String, String>();
		LocalDate effectiveDate = null;
		LocalDate endDate = null;
		
		asserNotNull(Constants.CLIENT_NAME,request.getClientName(),map);
		asserNotNull(Constants.CAMPAIGN_NAME,request.getCampaignName(),map);
		
		//boolean isEffectiveDateNull = asserNotNull(Constants.EFFECTIVE_DATE,request.getEffectiveDate(),map);
		
		if(!StringUtils.isEmpty(request.getEffectiveDate()))
			effectiveDate = validateDateFormate(Constants.EFFECTIVE_DATE,request.getEffectiveDate(),map);
		
		//boolean isEndDateNull = asserNotNull(Constants.END_DATE,request.getEndDate(),map);
		if(!StringUtils.isEmpty(request.getEffectiveDate()))
			endDate = validateDateFormate(Constants.END_DATE,request.getEndDate(),map);
		
		if(effectiveDate !=  null && endDate != null) {
			//JAVA 8 Feature
			if(effectiveDate.isAfter(endDate))
				map.put(Constants.EFFECTIVE_DATE, Constants.DATE_RANGE_ERROR);
		}
		return  map;
	}


}
