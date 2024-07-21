package com.pet.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.pet.db.AdoptionRepo;
import com.pet.db.AdoptionRequestRepo;
import com.pet.db.PetRepo;
import com.pet.model.Adoption;
import com.pet.model.AdoptionRequest;
import com.pet.model.Pet;

@CrossOrigin
@RestController
@RequestMapping("/api/adoptions")
public class AdoptionController {

	@Autowired
	private AdoptionRepo adoptionRepo;

	@Autowired
	private AdoptionRequestRepo adoptionRequestRepo;
	
	@Autowired
	private PetRepo petRepo;

	@GetMapping("/")
	public List<Adoption> getAllAdoptions() {
		List<Adoption> adoptions = adoptionRepo.findAll();

		List<Adoption> nonAdopted = new ArrayList<Adoption>();

		for (Adoption a : adoptions) {
			if (!a.getAdoptionRequest().getStatus().equals("ADOPTED")) {

				nonAdopted.add(a);

			}
		}
		return nonAdopted;
	}

	@GetMapping("/adopted")
	public List<Adoption> getAllAdoptedAdoptions() {
		List<Adoption> adoptions = adoptionRepo.findAll();

		List<Adoption> Adopted = new ArrayList<Adoption>();

		for (Adoption a : adoptions) {
			if (a.getAdoptionRequest().getStatus().equals("ADOPTED")) {

				Adopted.add(a);

			}
		}
		return Adopted;
	}

	@GetMapping("/{id}")
	public Adoption getAdoptionById(@PathVariable int id) {
		Optional<Adoption> a = adoptionRepo.findById(id);

		if (a.isPresent()) {
			return a.get();
		} else {
			System.err.println("Adoption[" + id + "] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption not found: id[" + id + "]");
		}

	}

	

	@GetMapping("/getadoptionsonhold/{id}")
	public List<Adoption> getAllAdoptionsOnhold(@PathVariable int id) {
		Adoption ad = new Adoption();

		Optional<AdoptionRequest> adoptedRequest = adoptionRequestRepo.findById(id);
		List<Adoption> adoptions = new ArrayList<Adoption>();
		List<Adoption> adoptionsOnhold = new ArrayList<Adoption>();

		if (adoptedRequest.isPresent()) {
			AdoptionRequest r = adoptedRequest.get();
			adoptions = adoptionRepo.findByAdoptionRequest(r);
			for (Adoption d : adoptions) {
				System.out.println(d);
				if (d.getAdoptionRequest().getStatus().equals("ONHOLD")) {
					adoptionsOnhold.add(d);
				}
			}
		} else
			System.out.println("Adoption does not exist");

		return adoptionsOnhold;
	}

	@PostMapping("")
	public Adoption addAdoption(@RequestBody Adoption adoption) {
		Pet pet = new Pet();
		  pet = petRepo.findById(adoption.getPet().getId())
	                .orElseThrow(() -> new RuntimeException("Pet not found"));
		
		pet.setAvailable(false);
		
		return adoptionRepo.save(adoption);
	}

	

	@PostMapping("/adopt/{id}")
	public Adoption completeAdoption(@PathVariable int id) {
		Optional<Adoption> adoption = adoptionRepo.findById(id);
		Adoption a = new Adoption();
		if (adoption.isPresent()) {
			a = adoption.get();
			a.setAdoptionCompletedDate(LocalDateTime.now());
			a.pet.setAvailable(false);
			a.setPaperWorkCompleted(true);
			a.setPaymentRecieved(true);
			a.getAdoptionRequest().setStatus("ADOPTED");
			
			return adoptionRepo.save(a);

		} else {
			System.err.println("Adoption[" + id + "] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption not found: id[" + id + "]");
		}

	}



	@PutMapping("/{id}")
	public Adoption UpdateAdoption(@PathVariable int id, @RequestBody Adoption adoption) {
		Adoption a = null;
		if (id != adoption.getId()) {
			System.err.println("Request Id [" + a.getId() + "] does not match path id[" + id + "].");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adoption not found");
		} else if (!adoptionRepo.existsById(id)) {
			System.err.println("Product does not exist for id: " + id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption does not exist");
		} else
			try {
				a = adoptionRepo.save(adoption);
			} catch (Exception e) {
				System.err.println(e);
				throw e;
			}

		return a;
	}

	@PostMapping("/save/{id}")
	public Adoption SaveAdoptionWithReason(@PathVariable int id, @RequestBody String rejectionReason) {
		Optional<Adoption> a = adoptionRepo.findById(id);
		Adoption adoption = null;
		if (a.isPresent()) {
			adoption = a.get();
			adoption.getAdoptionRequest().setRejectionReason(rejectionReason);
		} else {
			System.err.println("Adoption does not exist for id: " + id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption does not exist");
		}

		try {
			adoption = adoptionRepo.save(adoption);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}

		return adoption;
	}

	@DeleteMapping("/{id}")
	public boolean deleteAdoption(@PathVariable int id) {
		boolean success = false;

		if (adoptionRepo.existsById(id)) {
			adoptionRepo.deleteById(id);
			success = true;
		} else {
			System.err.println("Delete Error: No adoption exists for id:" + id);
			success = false;
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption does not exist");
		}

		return success;
	}

}