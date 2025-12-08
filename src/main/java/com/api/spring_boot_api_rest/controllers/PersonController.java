package com.api.spring_boot_api_rest.controllers;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.spring_boot_api_rest.services.PersonService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.api.spring_boot_api_rest.entities.Person;
import com.api.spring_boot_api_rest.entities.dtos.CreatePersonDTO;

@RestController
@RequestMapping("/person")
public class PersonController {
	@Autowired
	PersonService personService;

	@GetMapping("")
	public ResponseEntity<Page<Person>> getAll(
			@RequestParam(required = false) String name,
			@PageableDefault(page = 0, size = 5, sort = "name") Pageable pageable) {
		Page<Person> personsPage = personService.getAllPersonsList(name, pageable);
		return ResponseEntity.ok(personsPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOnePersonById(@PathVariable BigInteger id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(personService.getOneById(id));
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}

	@PostMapping("")
	public ResponseEntity<?> createNewPerson(@Valid @RequestBody CreatePersonDTO dto) {
		try {
			Person newPerson = personService.createPerson(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(newPerson);
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Error updating person: " + e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updatePerson(
			@PathVariable BigInteger id,
			@Valid @RequestBody CreatePersonDTO person) {
		try {
			Person updatedPerson = personService.updatePerson(id, person);
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedPerson);

		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("Error updating person: " + e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePerson(@PathVariable BigInteger id) {
		try {
			personService.deleteById(id);
			return ResponseEntity.noContent().build();

		} catch (EmptyResultDataAccessException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Person with ID " + id + " not found.");

		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body("An unexpected error occurred: " + e.getMessage());
		}
	}

}
