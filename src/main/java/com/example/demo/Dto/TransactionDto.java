package com.example.demo.Dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
public class TransactionDto {
    private String documentNo;
    private String processType;
    private Date transactionDate;
    private Time transactionTime;
    private String processAmount;

}
