package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

// bu kısımda operation type kısmı var bu eksik.
//buradan ekleme ve döküman da çkarabilecek.

@Getter
@Setter
@Data
@Entity
@Table(name = "expence_invoice")
public class ExpenseInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expence_id")
    private Long expence_id;

    @ManyToOne
    @JoinColumn(name = "stockCode", nullable = false)
    private Stock stockCode;

    @ManyToOne
    @JoinColumn(name = "client", nullable = false)
    private Client clientId;
//    @Column(name = "barcode" , nullable = false)   // gerek yok stock id ile çekilicek
//    private BigDecimal barcode;

//    @Column(name = "stockName" , nullable = false)
//    private String stockName;

    @Column(name = "quantity" , nullable = false) // Bunu stocktan da çekebiliriz
    private Integer quantity;

//    @Column(name = "unit" , nullable = false)
    //   private String unit;


    @Column(name = "price" , nullable = false)
    private BigDecimal price;

    @Column(name = "date" , nullable = false)
    private Date date;

    public ExpenseInvoice() {
    }


    public ExpenseInvoice(Stock stockCode, Client clientId, Integer quantity, Date date, BigDecimal price) {
        this.stockCode=stockCode;
        this.clientId=clientId;
        //       this.barcode=barcode;
        //     this.stockName=stockName;
        this.quantity=quantity;
        //      this.unit=unit;
        this.date=date;
        this.price=price;
    }
}


