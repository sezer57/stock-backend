package com.example.demo.Dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseInvoiceDto {
    private Integer quantity;
    private Long stockCode;
    private Long clientId;
    private BigDecimal price;
    private LocalDate date;
}
