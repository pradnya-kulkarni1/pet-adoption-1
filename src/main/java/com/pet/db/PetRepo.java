package com.pet.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.model.Breed;
import com.pet.model.Pet;
public interface PetRepo extends JpaRepository<Pet, Integer>{

	List<Pet> findByGenderAndAvailable(String string, boolean available);

	List<Pet> findByAvailable(boolean available);

	List<Pet> findByBreed(Optional<Breed> bd);


}