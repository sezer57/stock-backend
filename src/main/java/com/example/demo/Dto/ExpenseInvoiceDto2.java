package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseInvoiceDto2 {
    private Long expense_id;
    private Long stockId;
    private String stockName;
    private BigDecimal price;
    private int quantity;
    private LocalDate date;
}
