package com.api.spring_boot_api_rest.entities;

import java.math.BigInteger;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private BigInteger id;
  private String nit;
  private String name;
  private String address;
  private String phone_number;

  @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EducationalCredentials> educational_credentials;

}
