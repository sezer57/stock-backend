package com.example.demo.Dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InformationCodeDto {

    private Integer documentNumber;
    private String processType;
    private Date transactionDate;
    private Date transactionTime;
    private Long processAmount;
    private String status;
}
