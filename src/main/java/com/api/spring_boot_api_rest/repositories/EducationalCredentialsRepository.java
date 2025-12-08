package com.api.spring_boot_api_rest.repositories;

import java.math.BigInteger;

import com.api.spring_boot_api_rest.entities.EducationalCredentials;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationalCredentialsRepository extends ListCrudRepository<EducationalCredentials, BigInteger>  {

}
