package com.pet.model;

import jakarta.persistence.*;

@Entity
public class Species {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private float adoptionFee;
	
	
	public Species() {
		super();
	}


	public Species(int id, String name, float adoptionFee) {
		super();
		this.id = id;
		this.name = name;
		this.adoptionFee = adoptionFee;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public float getAdoptionFee() {
		return adoptionFee;
	}


	public void setAdoptionFee(float adoptionFee) {
		this.adoptionFee = adoptionFee;
	}


	@Override
	public String toString() {
		return "Species [id=" + id + ", name=" + name + ", adoptionFee=" + adoptionFee + "]";
	}
	
	

}