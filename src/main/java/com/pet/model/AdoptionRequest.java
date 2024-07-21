package com.pet.model;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AdoptionRequest {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	public User user;
	@ManyToOne
	@JoinColumn(name = "customerId")
	public Customer customer;
	
	private String status;
	
	private boolean backgroundCheck;
	public boolean isBackgroundCheck() {
		return backgroundCheck;
	}

	public void setBackgroundCheck(boolean backgroundCheck) {
		this.backgroundCheck = backgroundCheck;
	}

	public boolean isReferenceCheck() {
		return referenceCheck;
	}

	public void setReferenceCheck(boolean referenceCheck) {
		this.referenceCheck = referenceCheck;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	private boolean referenceCheck;
	private String rejectionReason;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "adoptionRequest [id=" + id + ", user=" + user + ", customer=" + customer +"status= "+status +"reference=" + reference
				+ ", submittedDate=" + submittedDate + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public LocalDateTime getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDateTime submittedDate) {
		this.submittedDate = submittedDate;
	}

	@ManyToOne
	@JoinColumn(name = "referenceId")
	public Reference reference;
	
	private LocalDateTime submittedDate;

}
