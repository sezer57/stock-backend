package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

// bu kısımda operation type kısmı var bu eksik.
//buradan ekleme ve döküman da çkarabilecek.

@Getter
@Setter
@Data
@Entity
@Table(name = "Expence Invoice")
public class ExpenseInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "ExpenseInvoiceID" , nullable = false)
    private Long ExpenseInvoiceID;
    @Column(name = "ClientID" , nullable = false)
    private Integer ClientID;

    @Column(name = "CurrencyAmount")
    private Integer CurrencyAmount;


    @Column(name = "BillNo"  , nullable = false)
    private Integer BillNo;

    @Column(name = "BillDate" , nullable = false)
    private Date BillDate;

    @Column(name = "Due")
    private String Due;

    @Column(name = "CustomCode")
    private Integer CustomCode;

    @Column(name = "Comment")
    private String Comment;

    @Column(name = "TaxOffice")
    private String TaxOffice;

    @Column(name = "CommercialTitle")
    private String CommercialTitle;

    public ExpenseInvoice() {
    }

    public ExpenseInvoice(Integer ClientID, Integer BillNo, Date BillDate, String Due, Integer CustomCode, String Comment, String TaxOffice, String CommercialTitle, Integer CurrencyAmount){

        this.ClientID=ClientID;
        this.BillNo=BillNo;
        this.BillDate=BillDate;
        this.Due=Due;
        this.CustomCode=CustomCode;
        this.Comment=Comment;
        this.TaxOffice=TaxOffice;
        this.CommercialTitle=CommercialTitle;
        this.CurrencyAmount=CurrencyAmount;
    }

}


