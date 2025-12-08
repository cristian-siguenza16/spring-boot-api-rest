package com.api.spring_boot_api_rest.services;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.spring_boot_api_rest.entities.EducationalCredentials;
import com.api.spring_boot_api_rest.entities.dtos.EducationalCredentialsDTO;
import com.api.spring_boot_api_rest.repositories.EducationalCredentialsRepository;

@Service
public class EducationalCredentialsService {
  @Autowired
	EducationalCredentialsRepository educationalCredentialsRepository;

	public List<EducationalCredentials> getAllEducationalCredentials() {
    return educationalCredentialsRepository.findAll();
  }

  public EducationalCredentials getOneById(BigInteger id) {
		return educationalCredentialsRepository.findById(id).orElse(null);
	}

	public EducationalCredentials createEducationalCredentials(EducationalCredentialsDTO dto) {

    EducationalCredentials newCredential = new EducationalCredentials();
    newCredential.setType(dto.getType().name()); 
    newCredential.setOrganization(dto.getOrganization());
    newCredential.setAcquired_credential(dto.getAcquired_credential());
    newCredential.setYear(dto.getYear());
    
    return educationalCredentialsRepository.save(newCredential);
  }

  public EducationalCredentials updateEducationalCredentials(BigInteger id, EducationalCredentialsDTO dto) {
    EducationalCredentials updEducationalCredentials = getOneById(id);
    
    if (updEducationalCredentials == null) {
        return null; 
    }

    if (dto.getType() != null) {
          updEducationalCredentials.setType(dto.getType().name());
    }
    if (dto.getOrganization() != null) {
        updEducationalCredentials.setOrganization(dto.getOrganization());
    }
    if (dto.getAcquired_credential() != null) {
        updEducationalCredentials.setAcquired_credential(dto.getAcquired_credential());
    }
    if (dto.getYear() != null) {
        updEducationalCredentials.setYear(dto.getYear());
    }

    return educationalCredentialsRepository.save(updEducationalCredentials);
  }

  public void updateCredentialFields(EducationalCredentials existing, EducationalCredentialsDTO dto) {
    if (dto.getType() != null) {
         existing.setType(dto.getType().name());
    }
    if (dto.getOrganization() != null) {
        existing.setOrganization(dto.getOrganization());
    }
    if (dto.getAcquired_credential() != null) {
        existing.setAcquired_credential(dto.getAcquired_credential());
    }
    if (dto.getYear() != null) {
        existing.setYear(dto.getYear());
    }
  }

	public void deleteById(BigInteger id) {
		educationalCredentialsRepository.deleteById(id);
	}
}
