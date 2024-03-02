package com.example.demo.Dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PurchaseDto {
    private BigDecimal barcode;
    private String stockName;
    private Integer quantity;
    private String unit;
    private BigDecimal price;
    private Date date;
}
