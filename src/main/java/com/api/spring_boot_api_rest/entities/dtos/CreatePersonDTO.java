package com.api.spring_boot_api_rest.entities.dtos;

import java.math.BigInteger;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
public class CreatePersonDTO {
    private BigInteger id;

    @NotBlank(message = "The NIT is required.")
    @Size(max = 10, message = "The NIT must not exceed 10 characters.")
    private String nit; 

    @NotBlank(message = "The name is required.")
    @Size(max = 60, message = "The name must not exceed 60 characters.")
    private String name; 

    @Size(max = 100, message = "The address must not exceed 100 characters.")
    private String address;

    @Size(max = 16, message = "The phone number must not exceed 16 characters.")
    private String phone_number;

    @Valid 
    @NotNull(message = "The educational credentials list cannot be null.")
    @NotEmpty(message = "At least one educational credential is required.")
    @Size(min = 1, message = "At least one educational credential is required.")
    private List<EducationalCredentialsDTO> educational_credentials;
}
