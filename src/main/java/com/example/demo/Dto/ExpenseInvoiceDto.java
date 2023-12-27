package com.example.demo.Dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExpenseInvoiceDto {

    private Integer ClientID;
    private Integer CurrencyAmount;
    private Integer BillNo;
    private Date BillDate;
    private String Due;
    private Integer CustomCode;
    private String Comment;
    private String TaxOffice;
    private String CommercialTitle;



}
