package com.pet.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pet.model.AdoptReference;

public interface AdoptReferenceRepo extends JpaRepository<AdoptReference, Integer> {

}