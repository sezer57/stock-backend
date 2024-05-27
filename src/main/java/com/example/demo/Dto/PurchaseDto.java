package com.example.demo.Dto;


import com.example.demo.model.Stock;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class PurchaseDto {

    private List<Integer> quantity;
    private List<Long> stockCode;
    private Long clientId;
    private String autherized;
    private List<BigDecimal> price;
    private BigDecimal vat;
    private LocalDate date;
}
