package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @Column(name = "autherized", nullable = false)
    private String autherized;
    @Column(name = "date" , nullable = false)
    private LocalDateTime date;
    public PurchaseInvoice(){}

    public PurchaseInvoice(  Client clientId,  LocalDateTime date, List<InvoiceP> invoices,String autherized) {

        this.clientId=clientId;

        //      this.unit=unit;
        this.date=date;
        this.invoices= invoices;
        this.autherized=autherized;
    }
}

