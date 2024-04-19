package com.example.demo.Dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BalanceDto {
    private Long clientID;
    private String debitCreditStatus;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal cash;
    private BigDecimal balance;
    private String comment;
}
