package com.bank.Trust_banking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String email;
    private String name;
    private Long userId;
}