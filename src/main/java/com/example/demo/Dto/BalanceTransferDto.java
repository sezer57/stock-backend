package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BalanceTransferDto {
    private Long balanceTransferId;
    private Long client;
    private Long system;
    private BigDecimal turnoverDebitAmount;
    private BigDecimal turnoverCreditAmount;
    private String comment;
}
