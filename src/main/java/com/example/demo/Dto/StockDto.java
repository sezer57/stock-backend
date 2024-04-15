package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class StockDto {
    private LocalDate registrationDate;
    private String stockName;
    private String stockCode;
    private String barcode;
    private String groupName;
    private String middleGroupName;
    private String unit;
    private BigDecimal salesPrice;
    private BigDecimal purchasePrice;
    private List<Long> warehouse_id;

}
