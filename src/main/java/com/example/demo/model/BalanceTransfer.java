package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@Data
@Entity
@Table(name = "BalanceTransfers")
public class BalanceTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_transfer_ID")
    private Long balance_transfer_ID;

    @ManyToOne
    @JoinColumn(name = "client", nullable = false)
    private Balance client;

    @ManyToOne
    @JoinColumn(name = "system", nullable = false)
    private Balance system;

    @Column(name = "transferDebitAmount" )
    private BigDecimal turnoverDebitAmount;

    @Column(name = "transferCreditAmount" )
    private BigDecimal turnoverCreditAmount;

    @Column(name = "transfer_date")
    private String date;

    @Column(name = "comment")
    private String comment;

    public BalanceTransfer(){}
    public BalanceTransfer(Balance client, Balance system, BigDecimal turnoverDebitAmount, BigDecimal turnoverCreditAmount, String date, String comment) {
        this.client = client;
        this.system = system;
        this.turnoverDebitAmount = turnoverDebitAmount;
        this.turnoverCreditAmount = turnoverCreditAmount;
        this.date = date;
        this.comment=comment;
    }
}
