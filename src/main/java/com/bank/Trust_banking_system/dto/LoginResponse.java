package com.bank.Trust_banking_system.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private String email;
    private String name;
    private Long userId;
}