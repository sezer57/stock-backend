package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ClientDto {
    private Integer clientCode;
    private String commercialTitle;
    private String name;
    private String surname;
    private String address;
    private String country;
    private String city;
    private String phone;
    private String gsm;
    private Date registrationDate;
}
