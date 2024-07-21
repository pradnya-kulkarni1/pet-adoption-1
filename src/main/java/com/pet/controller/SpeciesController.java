package com.pet.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.pet.db.SpeciesRepo;
import com.pet.model.Species;

@CrossOrigin
@RestController
@RequestMapping("/api/species")
public class SpeciesController {

	@Autowired
	private SpeciesRepo speciesRepo;
	
	@GetMapping("/")
	public List<Species> getAllProducts()
	{
		return speciesRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Species getSpeciesById(@PathVariable int id)
	{
		Optional<Species> s = speciesRepo.findById(id);
		
		if(s.isPresent())
		{
			return s.get();
		}else {
			System.err.println("Species["+id+"] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Species not found: id["+id+"]");
		}
		
	}
	
	@PostMapping("")
	public Species addSpecies (@RequestBody Species species)
	{
		return speciesRepo.save(species);
	}
	
	@PutMapping("/{id}")
	public Species UpdateSpecies(@PathVariable int id, @RequestBody Species species)
	{
		Species s = null;
		if(id!=species.getId())
		{
			System.err.println("Request Id ["+s.getId()+"] does not match path id[" +id+"].");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Species not found");
		} else if (!speciesRepo.existsById(id))
		{
			System.err.println("Specie does not exist for id: "+id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Species does not exist");
		}
		else
			try {
				s = speciesRepo.save(species);
			}
		catch (Exception e) { System.err.println(e); throw e;}
		
		return s;
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteSpecies(@PathVariable int id) {
		boolean success = false;
		
		if(speciesRepo.existsById(id)) {
			speciesRepo.deleteById(id);
			success = true;
		}
		else {
			System.err.println("Delete Error: No species exists for id:"+id);
			success = false;
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Species does not exist");
		}
		
		
		return success;
	}
	
}