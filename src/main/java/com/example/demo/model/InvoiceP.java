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
@Table(name = "invoices_p")
public class InvoiceP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long invoiceId;

    @ManyToOne
    @JoinColumn(name = "purchase_id" )
    private PurchaseInvoice purchase;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "vat ", nullable = false)
    private BigDecimal vat;
    public InvoiceP(){}


    public InvoiceP(Stock stock, Integer quantity, BigDecimal price,BigDecimal vat) {

        this.purchase = purchase;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.vat =vat;
    }

}
