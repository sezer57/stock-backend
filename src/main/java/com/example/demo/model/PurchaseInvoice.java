package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    @JoinColumn(name = "client", nullable = false)
    private Client clientId;
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceP> invoices;

    @Column(name = "date" , nullable = false)
    private LocalDate date;
    public PurchaseInvoice(){}

    public PurchaseInvoice(  Client clientId,  LocalDate date, List<InvoiceP> invoices) {

        this.clientId=clientId;

        //      this.unit=unit;
        this.date=date;
        this.invoices= invoices;
    }
}

