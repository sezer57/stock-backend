package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PurchaseDto2 {
    private Long purchase_id;
    private Long stockId;
    private String stockName;
    private BigDecimal price;
    private int quantity;
    private String clientName;
    private String clientAdress;
    private String clientPhone;
    private LocalDate date;
}
