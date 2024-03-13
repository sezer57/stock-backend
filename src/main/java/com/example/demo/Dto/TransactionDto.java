package com.example.demo.Dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionDto {
    private String documentNo;
    private String processType;
    private LocalDate transactionDate;
    private Time transactionTime;
    private String processAmount;

}
