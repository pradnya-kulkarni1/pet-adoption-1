package com.pet.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pet.model.AdoptionRequest;

public interface AdoptionRequestRepo extends JpaRepository <AdoptionRequest, Integer>{

}
