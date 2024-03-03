package com.example.demo.Dto;


import com.example.demo.model.Stock;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PurchaseDto {


    private Integer quantity;
    private Long stockCode;
    private Long clientId;
    private BigDecimal price;
    private Date date;
}
