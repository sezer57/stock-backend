package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class BalanceTransferDto {
    private Long balanceTransferId;
    private Long client;
    private BigDecimal system;
    private BigDecimal amount;
    private String paymentType;
    private String comment;
    private LocalDate date;
}
