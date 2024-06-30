package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ExpenseInvoiceDto2 {
    private Long expense_id;
    private List<Long> stockIds;
    private List<String> stockName;
    private List<String> price;
    private List<String> quantity;
    private List<Double> vat;
    private String autherized;
    private String clientName;
    private String clientAdress;
    private String clientPhone;
    private LocalDateTime date;
}
