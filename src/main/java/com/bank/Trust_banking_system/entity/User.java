package com.bank.Trust_banking_system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name="users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;

    private Integer age;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;

    private LocalDateTime createdAt;
}