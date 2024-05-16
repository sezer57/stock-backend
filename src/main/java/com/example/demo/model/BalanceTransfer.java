package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.math.BigDecimal;


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

    @JoinColumn(name = "clientId", nullable = false)
    private Long clientId;

    @JoinColumn(name = "system", nullable = false)
    private BigDecimal balance;

    @Column(name = "Amount" )
    private BigDecimal amount;

    @Column(name = "paymentType" )
    private String paymentType;

    @Column(name = "transfer_date")
    private LocalDate date;

    @Column(name = "comment")
    private String comment;

    public BalanceTransfer(){}
    public BalanceTransfer(Long clientId, BigDecimal balance, BigDecimal amount, String paymentType, LocalDate date, String comment) {
        this.clientId = clientId;
        this.balance = balance;
        this.amount = amount;
        this.paymentType = paymentType;
        this.date = date;
        this.comment=comment;
    }
}
