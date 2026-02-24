package com.bank.Trust_banking_system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name="users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String fullName;
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Phone required")
    private String phoneNumber;

    @Min(value = 18, message = "Age must be above 18")
    private Integer age;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;

    private LocalDateTime createdAt;
}