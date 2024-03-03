package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@Data
@Entity
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "infoCode")
    private String infoCode;

    @Column(name = "documentNo" , nullable = false)
    private String documentNo;

    @Column(name = "processType" , nullable = false)
    private String processType;

    @Column(name = "transactionDate" , nullable = false)
    private Date transactionDate;

    @Column(name = "transactionTime" , nullable = false)
    private Date transactionTime;

    @Column(name = "processAmount" , nullable = false)
    private String processAmount;

    public Transaction(String documentNo, String processType, Date transactionDate, Time transactionTime, String processAmount) {
        this.documentNo=documentNo;
        this.processType=processType;
        this.transactionDate=transactionDate;
        this.transactionTime=transactionTime;
        this.processAmount=processAmount;
    }


    public Transaction() {

    }
}
