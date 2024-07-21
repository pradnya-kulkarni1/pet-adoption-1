package com.pet.model;

import jakarta.persistence.*;

@Entity
public class Breed {

	@Override
	public String toString() {
		return "Breed [id=" + id + ", species=" + species + ", name=" + name + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "speciesId")
	public Species species;
	private String name;
	
	public Breed() {
		super();
	}

	public Breed(int id, Species species, String name) {
		super();
		this.id = id;
		this.species = species;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
