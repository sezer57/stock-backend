package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.Date;

public class Purchase {

// satın alma faturası (ticaret için yapılan ürünlerin faturası bu kısımda olacak)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stockCode")
    private BigDecimal stockCode;

    @Column(name = "barcode" , nullable = false)
    private BigDecimal barcode;

    @Column(name = "stockName" , nullable = false)
    private String stockName;

    @Column(name = "quantity" , nullable = false) // Bunu stocktan da çekebiliriz
    private Integer quantity;

    @Column(name = "unit" , nullable = false)
    private String unit;


    @Column(name = "price" , nullable = false)
    private BigDecimal price;

    @Column(name = "date" , nullable = false)
    private Date date;



    public Purchase(BigDecimal stockCode, BigDecimal barcode, String stockName, Integer quantity, String unit, Date date,BigDecimal price ){

        this.stockCode=stockCode;
        this.barcode=barcode;
        this.stockName=stockName;
        this.quantity=quantity;
        this.unit=unit;
        this.date=date;
        this.price=price;


    }
}


