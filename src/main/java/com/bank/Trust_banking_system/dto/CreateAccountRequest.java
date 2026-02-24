package com.bank.Trust_banking_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAccountRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotBlank(message = "Account type is required")
    private String accountType;

    @NotBlank(message = "Branch name is required")
    private String branchName;
}