package com.pet.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pet.model.Reference;


public interface ReferenceRepo extends JpaRepository<Reference, Integer>{

}
