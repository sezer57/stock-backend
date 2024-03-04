package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PurchaseDto2 {
    private Long purchase_id;
    private Long stockId;
    private String stockName;
    private BigDecimal price;
    private int quantity;
    private Date date;
}
