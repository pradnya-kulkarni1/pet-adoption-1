package com.pet.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pet.model.User;



public interface UserRepo extends JpaRepository<User, Integer>{

	User findByEmailAndPassword(String email, String password);

	

}