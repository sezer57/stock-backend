package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;

@Getter
@Setter
@Data
@Entity
@Table(name = "client")
public class  Client {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientID")
    private Long clientId;

    @Column(name = "clientCode" , nullable = false)
    private String clientCode;

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

    @Column(name = "registrationDate" , nullable = false)
    private LocalDate registrationDate;

    public Client() {}

    public Client(String clientCode,String commercialTitle,String name,String surname,String address,String country,String city,String phone,String gsm,LocalDate registrationDate) {
        this.clientCode=clientCode;
        this.commercialTitle=commercialTitle;
        this.name=name;
        this.surname=surname;
        this.address=address;
        this.country=country;
        this.city=city;
        this.phone=phone;
        this.gsm=gsm;
        this.registrationDate=registrationDate;
    }


}
