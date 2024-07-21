package com.pet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.pet.db.BreedRepo;
import com.pet.db.PetRepo;
import com.pet.model.Adoption;
import com.pet.model.Breed;
import com.pet.model.Pet;
import com.pet.model.Species;

@CrossOrigin
@RestController
@RequestMapping("/api/pets")
public class PetController {

	@Autowired
	private PetRepo petRepo;

	@Autowired
	private BreedRepo breedRepo;

	@GetMapping("/")
	public List<Pet> getAllPets() {
		return petRepo.findAll();
	}

	@GetMapping("/{id}")
	public Pet getPetById(@PathVariable int id) {
		Optional<Pet> p = petRepo.findById(id);

		if (p.isPresent()) {
			return p.get();
		} else {
			System.err.println("Pet[" + id + "] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found: id[" + id + "]");
		}

	}

	@GetMapping("/getPetsForBreeds/{id}")
	public List<Pet> getPetByBreeds(@PathVariable int id) {
		Optional<Breed> bd = breedRepo.findById(id);

		List<Pet> petsByBreed = new ArrayList<Pet>();
		List<Pet> availablePets = new ArrayList<Pet>();

		if (bd.isPresent()) {

			petsByBreed = petRepo.findByBreed(bd);

			for (Pet p : petsByBreed) {
				if (p.isAvailable()) {
					availablePets.add(p);
				}
			}
		} else {
			System.out.println("Pet doesnot exist");
		}

		return availablePets;

	}

	@GetMapping("/available")
	public List<Pet> getAllPetForAdopt() {
		Pet p = new Pet();
		boolean available = true;
		List<Pet> pets = petRepo.findByAvailable(available);
		// for (Adoption a :adoptions) System.out.println("Status = APPROVED "+ a);
		return pets;
	}

	@PostMapping("")
	public Pet addPet(@RequestBody Pet pet) {
		return petRepo.save(pet);
	}

	@PutMapping("/{id}")
	public Pet UpdatePet(@PathVariable int id, @RequestBody Pet pet) {
		Pet p = null;
		if (id != pet.getId()) {
			System.err.println("Request Id [" + p.getId() + "] does not match path id[" + id + "].");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet not found");
		} else if (!petRepo.existsById(id)) {
			System.err.println("Product does not exist for id: " + id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet does not exist");
		} else
			try {
				p = petRepo.save(pet);
			} catch (Exception e) {
				System.err.println(e);
				throw e;
			}

		return p;
	}

	@DeleteMapping("/{id}")
	public boolean deletePet(@PathVariable int id) {
		boolean success = false;

		if (petRepo.existsById(id)) {
			petRepo.deleteById(id);
			success = true;
		} else {
			System.err.println("Delete Error: No pet exists for id:" + id);
			success = false;
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet does not exist");
		}

		return success;
	}

}
