package com.api.spring_boot_api_rest.entities.dtos;

import java.math.BigInteger;

import com.api.spring_boot_api_rest.entities.enums.CredentialType;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
public class EducationalCredentialsDTO {
    
    private BigInteger id;

    @NotNull(message = "The type of credential is required.")
    private CredentialType type;
    
    @NotBlank(message = "The organization is required.")
    @Size(max = 60, message = "The organization must not exceed 60 characters.")
    private String organization;
    
    @NotBlank(message = "The acquired credential is required.")
    @Size(max = 100, message = "The acquired credential must not exceed 100 characters.")
    private String acquired_credential;
    
    @NotNull(message = "The year is required.")
    @Min(value = 1900, message = "The year must be after 1900.")
    @Max(value = 2100, message = "The year must be before 2100.")
    private Integer year;
}