package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@Entity
@Table(name = "Balances")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balanceID")
    private Long balanceID;

    @Column(name = "clientID" , nullable = false)
    private Long clientID;

    @Column(name = "debitCreditStatus" , nullable = false)
    private String debitCreditStatus;

    @Column(name = "turnoverDebit" , nullable = false)
    private BigDecimal turnoverDebit;

    @Column(name = "turnoverCredit" , nullable = false)
    private BigDecimal turnoverCredit;

    @Column(name = "turnoverBalance" , nullable = false)
    private BigDecimal turnoverBalance;

    @Column(name = "transactionalDebit" , nullable = false)
    private BigDecimal transactionalDebit;

    @Column(name = "transactionalCredit" , nullable = false)
    private BigDecimal transactionalCredit;

    @Column(name = "transactionalBalance" , nullable = false)
    private BigDecimal transactionalBalance;

    @Column(name = "comment" , nullable = false)
    private String comment;


    public Balance (Long clientID,String debitCreditStatus,BigDecimal turnoverDebit,BigDecimal turnoverCredit,BigDecimal turnoverBalance,BigDecimal transactionalDebit,BigDecimal transactionalCredit,BigDecimal transactionalBalance,String comment){
        this.clientID=clientID;
        this.debitCreditStatus=debitCreditStatus;
        this.turnoverDebit=turnoverDebit;
        this.turnoverCredit=turnoverCredit;
        this.turnoverBalance=turnoverBalance;
        this.transactionalDebit=transactionalDebit;
        this.transactionalCredit=transactionalCredit;
        this.transactionalBalance=transactionalBalance;
        this.comment=comment;
    }

    public Balance() {}
}
