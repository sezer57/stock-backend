package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    @Column(name = "expense_id")
    private Long expenseId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "autherized", nullable = false)
    private String autherized;
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices;

    public ExpenseInvoice() {
    }

    public ExpenseInvoice(  Client client,  List<Invoice> invoices,LocalDate date,String autherized) {

        this.client = client;
        this.date = date;
        this.invoices = invoices;
        this.autherized= autherized;
    }
}


