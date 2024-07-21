package com.pet.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.pet.db.CustomerRepo;
import com.pet.model.Customer;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerRepo customerRepo;
	
	@GetMapping("/")
	public List<Customer> getAllCustomers()
	{
		return customerRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Customer getCustomerById(@PathVariable int id)
	{
		Optional<Customer> c = customerRepo.findById(id);
		
		if(c.isPresent())
		{
			return c.get();
		}else {
			System.err.println("Customer["+id+"] does not exist");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer not found: id["+id+"]");
		}
		
	}
	
	
	@PostMapping("")
	public Customer addCustomer (@RequestBody Customer customer)
	{
		return customerRepo.save(customer);
	}
	
	@PutMapping("/{id}")
	public Customer UpdateCustomer(@PathVariable int id, @RequestBody Customer customer)
	{
		Customer c = null;
		if(id!=customer.getId())
		{
			System.err.println("Request Id ["+c.getId()+"] does not match path id[" +id+"].");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Customer not found");
		} else if (!customerRepo.existsById(id))
		{
			System.err.println("Product does not exist for id: "+id);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer does not exist");
		}
		else
			try {
				c = customerRepo.save(customer);
			}
		catch (Exception e) { System.err.println(e); throw e;}
		
		return c;
	}
	
	@DeleteMapping("/{id}")
	public boolean deleteCustomer(@PathVariable int id) {
		boolean success = false;
		
		if(customerRepo.existsById(id)) {
			customerRepo.deleteById(id);
			success = true;
		}
		else {
			System.err.println("Delete Error: No customer exists for id:"+id);
			success = false;
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer does not exist");
		}
		
		
		return success;
	}
	
}
