package com.campaign.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.campaign.model.Campaign;
import com.campaign.model.CampaignDetail;
import com.campaign.model.Client;
import com.campaign.model.PostRequest;
import com.campaign.model.Response;
import com.campaign.repository.CampaignRepository;
import com.campaign.repository.ClientRepository;
import com.campaign.service.CampaignService;

@Service
public class CampaignServiceImpl implements CampaignService {
	
	@Autowired
	CampaignRepository campaignRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	Validator validator;

	@Autowired
	ExceptionHandler exceptionHandler;
	
	@Override
	public Response addCampaign(PostRequest request) {

		Map<String,String> error = validator.validateAddCampaignFieldValues(request);
		Client client = null;
		if(!StringUtils.isEmpty(request.getClientName())) {
			client = clientRepository.findByClientName(request.getClientName());
			if(client == null) {
				error.put(Constants.CLIENT, Constants.CLIENT_NOT_EXISTS);
			}
		}
		
		if(!error.isEmpty()) {
			return exceptionHandler.throwBadRequest(error);
		}else {
			LocalDate currentDate = LocalDate.now();
			
			if(client != null && !(client.getEffectiveDate().isBefore(currentDate) && client.getEndDate().isAfter(currentDate)))
				error.put(Constants.CLIENT, Constants.CLIENT_INACTIVE);
			if(!error.isEmpty())
				return exceptionHandler.throwBadRequest(error);
		}
		
		Response response = new Response();
		Optional<Campaign> campaignList = campaignRepository.findByCampaignNameAndClientName(request.getCampaignName(),request.getClientName());
		
		if(campaignList.isEmpty()) {
			Campaign campaign = new Campaign();
			campaign.setCampaignName(request.getCampaignName());
			campaign.setClient(client);
			campaign.setCampaignDescription(request.getCampaignDescription());
			campaign.setEffectiveDate(LocalDate.parse(request.getEffectiveDate(), Constants.DATE_FORMAT));
			campaign.setEndDate(LocalDate.parse(request.getEndDate(), Constants.DATE_FORMAT));
			campaignRepository.save(campaign);
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setStatusMessage(Constants.CAMPAIGN_CREATED);
		}else {
			error.put(Constants.CAMPAIGN, Constants.CAMPAIGN_EXISTS);
			return exceptionHandler.throwBadRequest(error);
		}
		
		return response;
	}

	@Override
	public Response updateCampaign(PostRequest request) {

		Map<String,String> error = validator.validateUpdateCampaignFieldValues(request);
		
		if(!error.isEmpty()) {
			return exceptionHandler.throwBadRequest(error);
		}
		Response response = new Response();
		Optional<Campaign> campaignList = campaignRepository.findByCampaignNameAndClientName(request.getCampaignName(),request.getClientName());
		
		if(campaignList.isPresent()) {
			Campaign campaign = campaignList.get();
			if(!StringUtils.isEmpty(request.getCampaignDescription()))
				campaign.setCampaignDescription(request.getCampaignDescription());	
			
			if(!StringUtils.isEmpty(request.getEffectiveDate()))
				campaign.setEffectiveDate(LocalDate.parse(request.getEffectiveDate(), Constants.DATE_FORMAT));
			
			if(!StringUtils.isEmpty(request.getEndDate()))
				campaign.setEndDate(LocalDate.parse(request.getEndDate(), Constants.DATE_FORMAT));
			campaignRepository.save(campaign);
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setStatusMessage(Constants.CAMPAIGN_UPDATED);
		}else {
			error.put(Constants.CAMPAIGN, Constants.CAMPAIGN_NOT_EXISTS);
			return exceptionHandler.throwBadRequest(error);
		}
		
		return response;
	}

	@Override
	public List<CampaignDetail> getCampaignList(String clientName) {
		
		List<CampaignDetail> campaignDetailList = new ArrayList<>();
		List<CampaignDetail> sortedList = new ArrayList<>();
		List<Campaign> list = campaignRepository.findByClientName(clientName);
		
		if(!CollectionUtils.isEmpty(list)) {
			//JAVA 8
			list.stream().forEach(campaign -> {
				CampaignDetail detail = new CampaignDetail();
				detail.setCampaignName(campaign.getCampaignName());
				detail.setCampaignDescription(campaign.getCampaignDescription());
				detail.setEffectiveDate(campaign.getEffectiveDate());
				detail.setEndDate(campaign.getEndDate());
				campaignDetailList.add(detail);
			});
			
			
			//JAVA 8
			sortedList = campaignDetailList.stream()
					.sorted(Comparator.comparing(CampaignDetail::getEffectiveDate))
					.collect(Collectors.toList());
			
		}
		
		
		return sortedList;
	}

	public void setCampaignRepository(CampaignRepository campaignRepository) {
		this.campaignRepository = campaignRepository;
	}

	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

}
