package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountInfoDto {

    private Long clientId;
    private String name;
    private String code;
    private String accountNumber;
    private String Iban;
}
