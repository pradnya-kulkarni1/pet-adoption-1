package com.pet.db;

import com.pet.model.Adoption;
import com.pet.model.AdoptionRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface AdoptionRepo extends JpaRepository<Adoption, Integer>{

	Adoption save(Optional<Adoption> adoption);


	List<Adoption> findByAdoptionRequest(AdoptionRequest r);

}
