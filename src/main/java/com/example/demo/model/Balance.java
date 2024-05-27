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

    @Column(name = "Balance" , nullable = false)
    private BigDecimal balance;

    @Column(name = "comment" , nullable = false)
    private String comment;


    public Balance (Long clientID,String debitCreditStatus,BigDecimal debit,BigDecimal credit,BigDecimal cash,BigDecimal balance,String comment){
        this.clientID=clientID;
        this.debitCreditStatus=debitCreditStatus;
        this.debit=debit;
        this.credit=credit;
        this.cash=cash;
        this.balance=balance;
        this.comment=comment;
    }

    public Balance() {}


}
