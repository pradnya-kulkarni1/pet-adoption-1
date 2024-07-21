package com.pet.model;

import jakarta.persistence.*;

@Entity
public class AdoptReference {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "customerId")
	public Customer customer;
	@ManyToOne
	@JoinColumn(name = "referenceId")
	public Reference reference;
	
	public AdoptReference() {
		super();
	}

	public AdoptReference(int id, Customer customer, Reference reference) {
		super();
		this.id = id;
		this.customer = customer;
		this.reference = reference;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "AdoptReference [id=" + id + ", customer=" + customer + ", reference=" + reference + "]";
	}
	
	
	
	
}