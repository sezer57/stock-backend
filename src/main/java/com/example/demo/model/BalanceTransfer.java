package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
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

    @JoinColumn(name = "clientName", nullable = false)
    private String clientName;

    @JoinColumn(name = "clientSurname", nullable = false)
    private String clientSurname;

    @JoinColumn(name = "commericalTitle", nullable = false)
    private String commericalTitle;

    @JoinColumn(name = "system", nullable = false)
    private BigDecimal balance;

    @Column(name = "Amount" )
    private BigDecimal amount;

    @Column(name = "paymentType" )
    private String paymentType;

    @Column(name = "transfer_date")
    private LocalDateTime date;

    @Column(name = "comment")
    private String comment;

    public BalanceTransfer(){}
    public BalanceTransfer(Long clientId, String clientName, String clientSurname, String commericalTitle, BigDecimal balance, BigDecimal amount, String paymentType, LocalDateTime date, String comment) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.commericalTitle = commericalTitle;
        this.balance = balance;
        this.amount = amount;
        this.paymentType = paymentType;
        this.date = date;
        this.comment=comment;
    }

}
