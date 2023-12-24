package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
@Entity
@Table(name = "Information_code")
public class InformationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_codeId")
    private Long info_codeId;

    @Column(name = "documentNumber" , nullable = false)
    private Integer documentNumber;

    @Column(name = "processType" , nullable = false)
    private String processType;

    @Column(name = "transactionDate" , nullable = false)
    private Date transactionDate;

    @Column(name = "transactionTime" , nullable = false)
    private Date transactionTime;

    @Column(name = "processAmount" , nullable = false)
    private Long processAmount;

    @Column(name = "Status" , nullable = false)
    private String Status;


    public InformationCode() {}

    public InformationCode(Integer documentNumber, String processType,Date transactionDate,Date transactionTime,Long processAmount,String Status ){

        this.documentNumber=documentNumber;
        this.processType=processType;
        this.transactionDate=transactionDate;
        this.transactionTime=transactionTime;
        this.processAmount=processAmount;
        this.Status=Status;
    }



}
