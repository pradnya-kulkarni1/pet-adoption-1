package com.pet.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.pet.db.CustomerRepo;
import com.pet.db.ReferenceRepo;
import com.pet.db.UserRepo;
import com.pet.db.AdoptionRequestRepo;
import com.pet.model.Adoption;
import com.pet.model.AdoptionRequest;

@CrossOrigin
@RestController
@RequestMapping("/api/adoptionRequests")
public class AdoptionRequestController {
	
	@Autowired
	AdoptionRequestRepo adoptionRequestRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	ReferenceRepo referenceRepo;
	
	private static final String REVIEW ="REVIEW";
	private static final String APPROVED="APPROVED";

	
	@GetMapping("/")
	public List<AdoptionRequest> getAllAdoptionRequests() {
		List<AdoptionRequest> allRequest = adoptionRequestRepo.findAll();
		List<AdoptionRequest> nonadoptedReq = new ArrayList<AdoptionRequest>();
		for(AdoptionRequest ad:allRequest) {
			if(!"ADOPTED".equals(ad.getStatus())) {
				nonadoptedReq.add(ad);
			}
		}
		return nonadoptedReq;
	}

	@GetMapping("/{id}")
	public AdoptionRequest getAdoptionRequestById(@PathVariable int id) {
		Optional<AdoptionRequest> p = adoptionRequestRepo.findById(id);

		if (p.isPresent()) {
			return p.get();
		} else {
			System.err.println("AdoptionRequest[" + id + "] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "AdoptionRequest not found: id[" + id + "]");
		}

	}
	@GetMapping("/getadoptionsforapprove")
	public List<AdoptionRequest> getAllAdoptionsForApprove() {
		
		List<AdoptionRequest> adoptedRequest = adoptionRequestRepo.findAll();
		List<AdoptionRequest> adoptions = new ArrayList<AdoptionRequest>();

			for (AdoptionRequest d : adoptedRequest) {
				
				if ("APPROVED".equals(d.getStatus())) {
					adoptions.add(d);
				}

			}
		
		return adoptions;
	}
	
	@GetMapping("/getadoptionsonhold")
	public List<AdoptionRequest> getAllAdoptionsOhHold() {
		
		List<AdoptionRequest> adoptedRequest = adoptionRequestRepo.findAll();
		List<AdoptionRequest> adoptions = new ArrayList<AdoptionRequest>();

			for (AdoptionRequest d : adoptedRequest) {

				
				if ("ONHOLD".equals(d.getStatus())) {
					adoptions.add(d);
				}


			}
		


		return adoptions;
	}


	@PostMapping("")
	public AdoptionRequest addAdoptionRequest (@RequestBody AdoptionRequest adoptionRequest)
	{
		adoptionRequest.setStatus(REVIEW);
		adoptionRequest.setSubmittedDate(LocalDateTime.now());
		return adoptionRequestRepo.save(adoptionRequest);
	}

	@PostMapping("/approve/{id}")
	public AdoptionRequest approveAdoptionRequest(@PathVariable int id) {
		Optional<AdoptionRequest> adoption = adoptionRequestRepo.findById(id);
		AdoptionRequest a = new AdoptionRequest();
		if (adoption.isPresent()) {
			a = adoption.get();
			a.setBackgroundCheck(true);
			a.setReferenceCheck(true);
			a.setRejectionReason("");
			a.setStatus("APPROVED");
			;
			return adoptionRequestRepo.save(a);

		} else {
			System.err.println("Adoption[" + id + "] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption not found: id[" + id + "]");
		}

	}
	@PostMapping("/reject/{id}")
	public AdoptionRequest rejectAdoptionRequest(@PathVariable int id, @RequestBody String rejectionReason) {
		Optional<AdoptionRequest> adoption = adoptionRequestRepo.findById(id);
		AdoptionRequest a = new AdoptionRequest();
		if (adoption.isPresent()) {
			a = adoption.get();
			a.setRejectionReason(rejectionReason);
			a.setStatus("REJECTED");
			return adoptionRequestRepo.save(a);

		} else {
			System.err.println("Adoption[" + id + "] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption not found: id[" + id + "]");
		}

	}

	@PutMapping("/{id}")
	public AdoptionRequest UpdateAdoptionRequest(@PathVariable int id, @RequestBody AdoptionRequest adoptionRequest) {
		AdoptionRequest p = null;
		if (id != adoptionRequest.getId()) {
			System.err.println("Request Id [" + p.getId() + "] does not match path id[" + id + "].");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AdoptionRequest not found");
		} else if (!adoptionRequestRepo.existsById(id)) {
			System.err.println("Product does not exist for id: " + id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "AdoptionRequest does not exist");
		} else
			try {
				p = adoptionRequestRepo.save(adoptionRequest);
			} catch (Exception e) {
				System.err.println(e);
				throw e;
			}

		return p;
	}

	@PostMapping("/hold/{id}")
	public AdoptionRequest holdAdoption(@PathVariable int id) {
		Optional<AdoptionRequest> adoptionReq = adoptionRequestRepo.findById(id);
		AdoptionRequest a = new AdoptionRequest();
		if (adoptionReq.isPresent()) {
			a = adoptionReq.get();
			a.setStatus("ONHOLD");
			return adoptionRequestRepo.save(a);

		} else {
			System.err.println("Adoption[" + id + "] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption not found: id[" + id + "]");
		}

	}
	@DeleteMapping("/{id}")
	public boolean deleteAdoptionRequest(@PathVariable int id) {
		boolean success = false;

		if (adoptionRequestRepo.existsById(id)) {
			adoptionRequestRepo.deleteById(id);
			success = true;
		} else {
			System.err.println("Delete Error: No adoptionRequest exists for id:" + id);
			success = false;
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "AdoptionRequest does not exist");
		}

		return success;
	}



}
