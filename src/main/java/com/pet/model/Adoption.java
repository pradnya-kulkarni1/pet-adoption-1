package com.pet.model;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import com.pet.model.Customer;
import com.pet.model.User;
import com.pet.model.Pet;

@Entity
public class Adoption {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="petId")
	public Pet pet;
	
	@ManyToOne
	@JoinColumn(name="adoptionRequestId")
	public AdoptionRequest adoptionRequest;
	

	
	private LocalDateTime adoptionCompletedDate;
	
	private boolean paperWorkCompleted;
	
	public boolean isPaperWorkCompleted() {
		return paperWorkCompleted;
	}

	public void setPaperWorkCompleted(boolean paperWorkCompleted) {
		this.paperWorkCompleted = paperWorkCompleted;
	}

	public boolean isPaymentRecieved() {
		return paymentRecieved;
	}

	public void setPaymentRecieved(boolean paymentRecieved) {
		this.paymentRecieved = paymentRecieved;
	}

	private boolean paymentRecieved;
	
	public Adoption() {
		super();
	}

	public Adoption(int id, Pet pet, AdoptionRequest adoptionRequest, String rejectionReason,
		LocalDateTime adoptionCompletedDate, boolean backgroundCheck, boolean referenceCheck,
			boolean paperWorkCompleted, boolean paymentRecieved) {
		super();
		this.id = id;
		this.pet = pet;
		this.adoptionRequest = adoptionRequest;
		this.adoptionCompletedDate = adoptionCompletedDate;
		this.paperWorkCompleted = paperWorkCompleted;
		this.paymentRecieved = paymentRecieved;
	}

	public AdoptionRequest getAdoptionRequest() {
		return adoptionRequest;
	}

	public void setAdoptionRequest(AdoptionRequest adoptionRequest) {
		this.adoptionRequest = adoptionRequest;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}





	public LocalDateTime getAdoptionCompletedDate() {
		return adoptionCompletedDate;
	}

	public void setAdoptionCompletedDate(LocalDateTime localDateTime) {
		this.adoptionCompletedDate = localDateTime;
	}

	@Override
	public String toString() {
		return "Adoption [id=" + id + ", pet=" + pet 
				  + ",adoptionRequest="+ adoptionRequest
				+ ", adoptionCompletedDate=" + adoptionCompletedDate+ "]";
	}

	
	
	
}
