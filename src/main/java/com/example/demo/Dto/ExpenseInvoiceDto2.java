package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ExpenseInvoiceDto2 {
    private Long expense_id;
    private List<Long> stockIds;
    private List<String> stockName;
    private List<String> price;
    private List<String> quantity;
    private String autherized;
    private String clientName;
    private String clientAdress;
    private String clientPhone;
    private LocalDate date;
}
