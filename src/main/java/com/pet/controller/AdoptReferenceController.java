package com.pet.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.pet.db.AdoptReferenceRepo;
import com.pet.model.AdoptReference;

@CrossOrigin
@RestController
@RequestMapping("/api/adoptReferences")
public class AdoptReferenceController {

	@Autowired
	private AdoptReferenceRepo adoptReferenceRepo;
	
	@GetMapping("/")
	public List<AdoptReference> getAllAdoptReferences()
	{
		return adoptReferenceRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public AdoptReference getAdoptReferenceById(@PathVariable int id)
	{
		Optional<AdoptReference> a = adoptReferenceRepo.findById(id);
		
		if(a.isPresent())
		{
			return a.get();
		}else {
			System.err.println("AdoptReference["+id+"] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"AdoptReference not found: id["+id+"]");
		}
		
	}
	
	@PostMapping("")
	public AdoptReference addAdoptReference (@RequestBody AdoptReference adoptReference)
	{
		return adoptReferenceRepo.save(adoptReference);
	}
	
	@PutMapping("/{id}")
	public AdoptReference UpdateAdoptReference(@PathVariable int id, @RequestBody AdoptReference adoptReference)
	{
		AdoptReference a = null;
		if(id!=adoptReference.getId())
		{
			System.err.println("Request Id ["+a.getId()+"] does not match path id[" +id+"].");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"AdoptReference not found");
		} else if (!adoptReferenceRepo.existsById(id))
		{
			System.err.println("Product does not exist for id: "+id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"AdoptReference does not exist");
		}
		else
			try {
				a = adoptReferenceRepo.save(adoptReference);
			}
		catch (Exception e) { System.err.println(e); throw e;}
		
		return a;
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteAdoptReference(@PathVariable int id) {
		boolean success = false;
		
		if(adoptReferenceRepo.existsById(id)) {
			adoptReferenceRepo.deleteById(id);
			success = true;
		}
		else {
			System.err.println("Delete Error: No adoptReference exists for id:"+id);
			success = false;
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"AdoptReference does not exist");
		}
		
		
		return success;
	}
	
}