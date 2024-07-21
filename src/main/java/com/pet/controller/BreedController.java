package com.pet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.pet.db.BreedRepo;
import com.pet.db.SpeciesRepo;
import com.pet.model.Breed;
import com.pet.model.Species;

@CrossOrigin
@RestController
@RequestMapping("/api/breeds")
public class BreedController {

	@Autowired
	private BreedRepo breedRepo;
	
	@Autowired
	private SpeciesRepo speciesRepo; 
	
	@GetMapping("/")
	public List<Breed> getAllBreeds()
	{
		return breedRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Breed getBreedById(@PathVariable int id)
	{
		Optional<Breed> c = breedRepo.findById(id);
		
		if(c.isPresent())
		{
			
			return c.get();
		}else {
			System.err.println("Breed["+id+"] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Breed not found: id["+id+"]");
		}
		
	}
	
	@GetMapping("/getBreedsForSpecies/{id}")
	public List<Breed> getBreedBySpecies(@PathVariable int id)
	{
		Optional<Species> sp = speciesRepo.findById(id);
		
		List<Breed> breedsBySpecies = new ArrayList<Breed>();
		
		
		if(sp.isPresent()) {
			
		
			breedsBySpecies = breedRepo.findBySpecies(sp);
		}	
		else {
			System.out.println("Breed doesnot exist");
		}
		
		return breedsBySpecies;
		
	}
	
	@PostMapping("")
	public Breed addBreed (@RequestBody Breed breed)
	{
		return breedRepo.save(breed);
	}
	
	@PutMapping("/{id}")
	public Breed UpdateBreed(@PathVariable int id, @RequestBody Breed breed)
	{
		Breed c = null;
		if(id!=breed.getId())
		{
			System.err.println("Request Id ["+c.getId()+"] does not match path id[" +id+"].");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Breed not found");
		} else if (!breedRepo.existsById(id))
		{
			System.err.println("Product does not exist for id: "+id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Breed does not exist");
		}
		else
			try {
				c = breedRepo.save(breed);
			}
		catch (Exception e) { System.err.println(e); throw e;}
		
		return c;
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteBreed(@PathVariable int id) {
		boolean success = false;
		
		if(breedRepo.existsById(id)) {
			breedRepo.deleteById(id);
			success = true;
		}
		else {
			System.err.println("Delete Error: No breed exists for id:"+id);
			success = false;
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Breed does not exist");
		}
		
		
		return success;
	}
	
}