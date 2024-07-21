package com.pet.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.pet.db.ReferenceRepo;
import com.pet.model.Reference;

@CrossOrigin
@RestController
@RequestMapping("/api/references")
public class ReferenceController {

	@Autowired
	private ReferenceRepo referenceRepo;
	
	@GetMapping("/")
	public List<Reference> getAllReferences()
	{
		return referenceRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Reference getReferenceById(@PathVariable int id)
	{
		Optional<Reference> r = referenceRepo.findById(id);
		
		if(r.isPresent())
		{
			return r.get();
		}else {
			System.err.println("Reference["+id+"] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Reference not found: id["+id+"]");
		}
		
	}
	
	@PostMapping("")
	public Reference addReference (@RequestBody Reference reference)
	{
		return referenceRepo.save(reference);
	}
	
	@PutMapping("/{id}")
	public Reference UpdateReference(@PathVariable int id, @RequestBody Reference reference)
	{
		Reference r = null;
		if(id!=reference.getId())
		{
			System.err.println("Request Id ["+r.getId()+"] does not match path id[" +id+"].");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Reference not found");
		} else if (!referenceRepo.existsById(id))
		{
			System.err.println("Product does not exist for id: "+id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Reference does not exist");
		}
		else
			try {
				r = referenceRepo.save(reference);
			}
		catch (Exception e) { System.err.println(e); throw e;}
		
		return r;
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteReference(@PathVariable int id) {
		boolean success = false;
		
		if(referenceRepo.existsById(id)) {
			referenceRepo.deleteById(id);
			success = true;
		}
		else {
			System.err.println("Delete Error: No reference exists for id:"+id);
			success = false;
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Reference does not exist");
		}
		
		
		return success;
	}
	
}
