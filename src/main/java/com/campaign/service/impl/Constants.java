package com.campaign.service.impl;

import java.time.format.DateTimeFormatter;

public class Constants {

	public static final String CAMPAIGN_CREATED = "Campaign added successfully";
	public static final String CAMPAIGN_UPDATED = "Campaign updated successfully";
	
	//JAVA 8 Feature
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static final String CAMPAIGN = "Campaign";
	public static final String CAMPAIGN_EXISTS = "Campaign already exists";
	public static final String CAMPAIGN_NOT_EXISTS = "Campaign not exists";
	public static final String CLIENT = "Client";
	public static final String CLIENT_NOT_EXISTS = "Client not exists";
	public static final String CLIENT_INACTIVE = "Client is inactive";
	public static final String CARRIER = "carrier";
	public static final String CONTRACT_ID = "contractId";
	public static final String CLIENT_NAME = "clientName";
	public static final String CAMPAIGN_NAME = "campaignName;";
	public static final String CAMPAIGN_DESCRIPTION = "campaignDescription";
	public static final String EFFECTIVE_DATE = "effectiveDate";
	public static final String END_DATE = "endDate";
	public static final String ADMIN = "ADMIN";
	public static final String USER = "USER";
	public static final String DIALECT = "hibernate.dialect";
	
	
	public static final String DATE_RANGE_ERROR = "Effective Date should be always earlier than EndDate";
	public static final String DATE_FORMAT_ERROR = "Invalid date format. Please provide date in dd-MM-yyyy format";
	
	private Constants() {
		
	}
}
