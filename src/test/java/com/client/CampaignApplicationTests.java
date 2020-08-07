package com.client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import com.campaign.controller.Controller;
import com.campaign.model.Campaign;
import com.campaign.model.CampaignDetail;
import com.campaign.model.Client;
import com.campaign.model.PostRequest;
import com.campaign.model.Response;
import com.campaign.repository.CampaignRepository;
import com.campaign.repository.ClientRepository;
import com.campaign.service.impl.CampaignServiceImpl;
import com.campaign.service.impl.ExceptionHandler;
import com.campaign.service.impl.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional; 

@RunWith(MockitoJUnitRunner.class)
public class CampaignApplicationTests {

	 @Mock
	 ClientRepository clientRepository;
	 
	 @Mock
	 CampaignRepository campaignRepository;
	     
	 
	 Controller controller;
	 
	 @Before
	 public void postConstruct() {
		 MockitoAnnotations.initMocks(this);
		 controller = new Controller();
		 CampaignServiceImpl service = new CampaignServiceImpl();
		 service.setValidator(new Validator());
		 service.setExceptionHandler(new ExceptionHandler());
		 service.setClientRepository(clientRepository);
		 service.setCampaignRepository(campaignRepository);
		 controller.setCampaignService(service);
	 }
	  
	@Test
	public void testBadRequest() {
		PostRequest request = new PostRequest();
		request.setCampaignDescription(null);
		Response response = controller.addCampaign(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testClientNotExist() {
		PostRequest request = new PostRequest();
		request.setClientName("client");
		Response response = controller.addCampaign(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testDateRange() {
		PostRequest request = new PostRequest();
		request.setClientName("client");
		request.setEffectiveDate("28-07-2001");
		request.setEndDate("28-07-2000");
		Response response = controller.addCampaign(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testClientInactive() {
		
		PostRequest request = getPostRequest();
		Client client = getClientObject();
		client.setEndDate(LocalDate.of(2000, 07, 01));
		Mockito.when(clientRepository.findByClientName("client")).thenReturn(client);
		
		Response response = controller.addCampaign(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testCampaing() {
		
		PostRequest request = getPostRequest();
		Client client = getClientObject();
		Mockito.when(clientRepository.findByClientName("client")).thenReturn(client);
		
		Response response = controller.addCampaign(request);
		Assert.assertEquals(response.getStatusCode(),HttpStatus.OK.value());
	}
	
	@Test
	public void testCampaingExists() {
		
		PostRequest request = getPostRequest();
		Client client = getClientObject();
		
		Campaign campaign = getCampaign();
		Optional<Campaign> campaignList = Optional.of(campaign);
		
		Mockito.when(clientRepository.findByClientName("client")).thenReturn(client);
		Mockito.when(campaignRepository.findByCampaignNameAndClientName(request.getCampaignName(), 
				request.getClientName())).thenReturn(campaignList);
		
		Response response = controller.addCampaign(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void testUpdateCampaign() {
		
		PostRequest request = getPostRequest();
		
		Campaign campaign = getCampaign();
		Optional<Campaign> campaignList = Optional.of(campaign);
		
		Mockito.when(campaignRepository.findByCampaignNameAndClientName(request.getCampaignName(), 
				request.getClientName())).thenReturn(campaignList);
		
		Response response = controller.updateCampaign(request);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK.value());
	}
	
	@Test
	public void testGetCampaign() {
		
		Campaign campaign = new Campaign();
		campaign.setEffectiveDate(LocalDate.of(2001, 07, 01));
		campaign.setEndDate(LocalDate.of(2025, 07, 01));
		List<Campaign> list = new ArrayList<>();
		list.add(campaign);
		
		Mockito.when(campaignRepository.findByClientName("client")).thenReturn(list);
		
		List<CampaignDetail> campaignDetailList = controller.getCampaign("client");
		Assert.assertNotNull(campaignDetailList);
	}
	
	
	
	private Campaign getCampaign() {
		Campaign campaign = new Campaign();
		campaign.setCampaignName("campaignName");
		campaign.setCampaignDescription("campaignDescription");
		return campaign;
	}

	private PostRequest getPostRequest() {
		PostRequest request = new PostRequest();
		request.setClientName("client");
		request.setCampaignName("campaignName");
		request.setEffectiveDate("28-07-2001");
		request.setEndDate("28-07-2005");
		return request;
	}

	private Client getClientObject() {
		Client client = new Client();
		client.setClientName("client");
		client.setEffectiveDate(LocalDate.of(2001, 07, 01));
		client.setEndDate(LocalDate.of(2025, 07, 01));
		return client;
	}
	

}
