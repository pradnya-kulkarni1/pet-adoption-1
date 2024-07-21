package com.pet.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.model.Breed;
import com.pet.model.Species;

public interface BreedRepo extends JpaRepository<Breed, Integer>{

	List<Breed> findBySpecies(Optional<Species> sp);

	Optional<Breed> findBySpeciesId(int id);

	Optional<Breed> getBreedsBySpeciesId(int id);

}
