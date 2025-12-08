package com.api.spring_boot_api_rest.repositories;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.api.spring_boot_api_rest.entities.Person;


@Repository
public interface PersonRepository extends ListCrudRepository<Person, BigInteger> {
  Page<Person> findByNameContainingIgnoreCase(String name, Pageable pageable);
  Page<Person> findAll(Pageable pageable);
}
