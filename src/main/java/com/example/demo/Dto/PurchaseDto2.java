package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class PurchaseDto2 {
    private Long purchase_id;
    private List<Long> stockId;
    private List<String> stockName;
    private List<BigDecimal> price;
    private List<BigDecimal> vat;
    private List<Integer> quantity;
    private String autherized;
    private String clientName;
    private String clientAdress;
    private String clientPhone;
    private LocalDate date;
}
