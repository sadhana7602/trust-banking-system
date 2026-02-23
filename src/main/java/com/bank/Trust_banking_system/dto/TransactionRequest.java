package com.bank.Trust_banking_system.dto;



import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {

    private String accountNumber;
    private BigDecimal amount;
}
