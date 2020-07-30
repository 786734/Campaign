package com.campaign.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.campaign.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	Client findByClientName(String clientName);


}
