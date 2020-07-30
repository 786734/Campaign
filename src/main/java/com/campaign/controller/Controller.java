package com.campaign.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.campaign.model.CampaignDetail;
import com.campaign.model.PostRequest;
import com.campaign.model.Response;
import com.campaign.service.CampaignService;

@RestController
@RequestMapping("campaign")
public class Controller {
	
	@Autowired
	CampaignService campaignService;

	@PostMapping
	public Response addCampaign(@RequestBody PostRequest client) {
	    return campaignService.addCampaign(client);
	}   
	
	@PutMapping
	public Response updateCampaign(@RequestBody PostRequest client) {
	    return campaignService.updateCampaign(client);
	}
	
	@GetMapping
	public List<CampaignDetail> updateCampaign(@RequestParam("clientName") String clientName) {
	    return campaignService.getCampaignList(clientName);
	}
	
	
}
