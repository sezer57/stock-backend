package com.example.demo.Dto;


import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class InformationCodeDto {

    private Integer documentNumber;
    private String processType;
    private String transactionDate;
    private String transactionTime;
    private Long processAmount;
    private String status;
}
