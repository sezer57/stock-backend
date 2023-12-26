package com.example.demo.Dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BalanceDto {
    private Long clientID;
    private String debitCreditStatus;
    private BigDecimal turnoverDebit;
    private BigDecimal turnoverCredit;
    private BigDecimal turnoverBalance;
    private BigDecimal transactionalDebit;
    private BigDecimal transactionalCredit;
    private BigDecimal transactionalBalance;
    private String comment;
}
