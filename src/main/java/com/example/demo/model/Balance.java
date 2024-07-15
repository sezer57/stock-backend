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
@Table(name = "Balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BalanceID")
    private Long balanceID;

    @Column(name = "ClientID" , nullable = false)
    private Long clientID;

    @Column(name = "DebitCreditStatus" , nullable = false)
    private String debitCreditStatus;

    @Column(name = "Debit" , nullable = false)
    private BigDecimal debit;

    @Column(name = "Credit" , nullable = false)
    private BigDecimal credit;

    @Column(name = "Cash" , nullable = false)
    private BigDecimal cash;

    @Column(name = "BalancePayable" , nullable = false)
    private BigDecimal balanceDebt;

    @Column(name = "BalanceReceivable" , nullable = false)
    private BigDecimal balanceReceive;

    @Column(name = "comment" , nullable = false)
    private String comment;


    public Balance (Long clientID,String debitCreditStatus,BigDecimal debit,BigDecimal credit,BigDecimal cash,BigDecimal balanceDebt, BigDecimal balanceReceive,String comment){
        this.clientID=clientID;
        this.debitCreditStatus=debitCreditStatus;
        this.debit=debit;
        this.credit=credit;
        this.cash=cash;
        this.balanceDebt=balanceDebt;
        this.balanceReceive = balanceReceive;
        this.comment=comment;
    }

    public Balance() {}


}
