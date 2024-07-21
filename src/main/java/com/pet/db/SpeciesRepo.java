package com.pet.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.model.Species;

public interface SpeciesRepo extends JpaRepository<Species, Integer>{

}