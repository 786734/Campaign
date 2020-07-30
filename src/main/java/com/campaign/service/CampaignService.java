package com.campaign.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.campaign.model.CampaignDetail;
import com.campaign.model.PostRequest;
import com.campaign.model.Response;

@Service
public interface CampaignService {

	Response addCampaign(PostRequest request);

	Response updateCampaign(PostRequest client);

	List<CampaignDetail> getCampaignList(String clientName);

}
