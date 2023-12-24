package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
@Table(name = "BankAccountInfos")
public class BankAccountInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bankAccountId")
    private Long bankAccountId;

    @Column(name = "clientId" , nullable = false)
    private Long clientId;

    @Column(name = "name" , nullable = false)
    private String name;

    @Column(name = "code" , nullable = false)
    private String code;

    @Column(name = "accountNumber" , nullable = false)
    private String accountNumber;

    @Column(name = "iban" , nullable = false)
    private String iban;


    public BankAccountInfo(Long clientId,String name,String code,String accountNumber,String iban) {
        this.clientId=clientId;
        this.name = name;
        this.code =code;
        this.accountNumber=accountNumber;
        this.iban=iban;
    }

    public BankAccountInfo() {}
}
