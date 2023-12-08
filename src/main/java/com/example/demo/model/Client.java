package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.sql.Date;

@Getter
@Setter
@Data
@Entity
@Table(name = "client")
public class Client {



    // primary key müşteri ıd mi olmalı yoksa National Insurance Number(tc kimlik numarası mı olmalı) aynı isimde 2 müşteri olabilir;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientID")
    private Long clientId;

    @Column(name = "registrationDate" , nullable = false)
    private Date registrationDate;

    @Column(name = "clientCode" , nullable = false)
    private Integer clientCode;

    @Column(name = "commercialTitle" , nullable = false)
    private String commercialTitle;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "surname" , nullable = false)
    private String surname;

    @Column(name = "address" , nullable = false)
    private String address;

    @Column(name = "country" , nullable = false)
    private String country;

    @Column(name = "city" , nullable = false)
    private String city;

    @Column(name = "phone" , nullable = false)
    private String phone;

    @Column(name = "gsm" , nullable = false)
    private String gsm;

    public Client(Long clientId,Date registrationDate,Integer clientCode,String commercialTitle,String name,String surname,String address,String country,String city,String phone,String gsm) {
        this.clientId = clientId;
        this.registrationDate=registrationDate;
        this.clientCode=clientCode;
        this.commercialTitle=commercialTitle;
        this.name=name;
        this.surname=surname;
        this.address=address;
        this.country=country;
        this.city=city;
        this.phone=phone;
        this.gsm=gsm;
    }

    public Client() {}
}
