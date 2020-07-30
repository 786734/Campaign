package com.campaign.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="client")
public class Client implements Serializable{

	private static final long serialVersionUID = -6926231530442118997L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "client_id")
    private Long clientId;
	
	@Column(name = "client_name")
	private String clientName;
	
	@Column(name = "carrier")
	private String carrier;
	
	@Column(name = "contract_id")
	private String contractId;
	
	@Column(name = "effective_date")
	private LocalDate effectiveDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
}
