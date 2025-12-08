package com.api.spring_boot_api_rest.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.spring_boot_api_rest.repositories.PersonRepository;

import jakarta.transaction.Transactional;

import com.api.spring_boot_api_rest.entities.EducationalCredentials;
import com.api.spring_boot_api_rest.entities.Person;
import com.api.spring_boot_api_rest.entities.dtos.CreatePersonDTO;
import com.api.spring_boot_api_rest.entities.dtos.EducationalCredentialsDTO;

@Service
public class PersonService {
  @Autowired
	PersonRepository personRepository;
  @Autowired
  EducationalCredentialsService educationalCredentialsService;

	public Page<Person> getAllPersonsList(String name, Pageable pageable) {
    if (name != null && !name.trim().isEmpty()) {
        return personRepository.findByNameContainingIgnoreCase(name, pageable);
    } else {
        return personRepository.findAll(pageable);
    }
  }

  public Person getOneById(BigInteger id) {
		return personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
	}

  private EducationalCredentials mapEducationalCredentialDTOToEntity(
      EducationalCredentialsDTO dto, Person person
  ) 
  {
    EducationalCredentials entity = new EducationalCredentials();
    
    entity.setType(dto.getType().name()); 
    entity.setOrganization(dto.getOrganization());
    entity.setAcquired_credential(dto.getAcquired_credential());
    entity.setYear(dto.getYear());
    
    entity.setPerson(person); 
    
    return entity;
  }

	public Person createPerson(CreatePersonDTO dto) {
    // Handles the logic of map dto to a new person
    Person personToSave = new Person();
    personToSave.setNit(dto.getNit());
    personToSave.setName(dto.getName());
    personToSave.setAddress(dto.getAddress());
    personToSave.setPhone_number(dto.getPhone_number());

    // Handles the logic of mapping all credentials
    List<EducationalCredentials> credentials = dto.getEducational_credentials().stream()
        .map(eduDto -> mapEducationalCredentialDTOToEntity(eduDto, personToSave))
        .collect(Collectors.toList());

    personToSave.setEducational_credentials(credentials);
    
    // Handles the logic of save the person
    return personRepository.save(personToSave);
  }

  private void syncEducationalCredentials(
      Person person, 
      List<EducationalCredentialsDTO> incomingCredentialsDTO
  ) 
  {
    List<EducationalCredentials> existingCredentials = person.getEducational_credentials();
    List<EducationalCredentials> credentialsToKeep = new ArrayList<>();
    
    // Handles the logic of update all the existings educational credentials
    for (EducationalCredentialsDTO dto : incomingCredentialsDTO) {
        if (dto.getId() != null) {
            
            EducationalCredentials existing = existingCredentials.stream()
                .filter(cred -> cred.getId().equals(dto.getId()))
                .findFirst()
                .orElse(null);

            if (existing != null) {
                educationalCredentialsService.updateCredentialFields(existing, dto); 
                credentialsToKeep.add(existing);
                continue;
            }
        }
        
        EducationalCredentials newCredential = mapEducationalCredentialDTOToEntity(dto, person);
        credentialsToKeep.add(newCredential);
    }
    
    // Handles the logic of get all the credentials tath exists in the bd but 
    // does not exist on the updated one and delete them
    List<EducationalCredentials> credentialsToRemove = existingCredentials.stream()
        .filter(cred -> !credentialsToKeep.contains(cred))
        .collect(Collectors.toList());
    existingCredentials.removeAll(credentialsToRemove);
    
    // Handles the logic of create the new ones
    person.getEducational_credentials().clear();
    person.getEducational_credentials().addAll(credentialsToKeep);
  }

  private void updatePersonFields(Person findedPerson, CreatePersonDTO dto) {
    if (dto.getName() != null) {
        findedPerson.setName(dto.getName());
    }
    if (dto.getAddress() != null) {
        findedPerson.setAddress(dto.getAddress());
    }
    if (dto.getPhone_number() != null) {
        findedPerson.setPhone_number(dto.getPhone_number());
    }
  }

  @Transactional
  public Person updatePerson(BigInteger id, CreatePersonDTO dto) {
    Person findedPerson = getOneById(id);
    if (findedPerson == null) {
      return null; 
    }

    updatePersonFields(findedPerson, dto);

    if (dto.getEducational_credentials() != null || !dto.getEducational_credentials().isEmpty()) {
        syncEducationalCredentials(findedPerson, dto.getEducational_credentials());
    } else {
      throw new RuntimeException("At least one educational credentials is required.");
    }

    return personRepository.save(findedPerson);
  }

	public void deleteById(BigInteger id) {
		personRepository.deleteById(id);
	}
}
