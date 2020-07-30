package com.campaign.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.campaign.model.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

	@Query("SELECT c FROM Campaign c where c.campaignName=:campaignName and c.client.clientName=:clientName") 
	Optional<Campaign> findByCampaignNameAndClientName(@Param("campaignName") String campaignName, @Param("clientName") String clientName);

	@Query("SELECT c FROM Campaign c where c.client.clientName=:clientName") 
	List<Campaign> findByClientName(@Param("clientName") String clientName);


}
