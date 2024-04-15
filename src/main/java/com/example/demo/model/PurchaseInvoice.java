package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@Data
@Entity
@Table(name = "purchase")
public class PurchaseInvoice {

    // satın alma faturası (ticaret için yapılan ürünlerin faturası bu kısımda olacak)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long purchase_id;

    @ManyToOne
    @JoinColumn(name = "stockCode", nullable = false)
    private Stock stockCode;

    @ManyToOne
    @JoinColumn(name = "warehouseId", nullable = false)
    private Warehouse warehouseId;
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
    private LocalDate date;
    public PurchaseInvoice(){}

    public PurchaseInvoice(Stock stockCode, Warehouse warehouseId, Integer quantity, LocalDate date, BigDecimal price) {
        this.stockCode=stockCode;
        this.warehouseId=warehouseId;
        //       this.barcode=barcode;
        //     this.stockName=stockName;
        this.quantity=quantity;
        //      this.unit=unit;
        this.date=date;
        this.price=price;
    }
}


