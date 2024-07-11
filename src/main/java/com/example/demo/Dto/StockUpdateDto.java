package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class StockUpdateDto {
    // stock update için front düzelticek
    private Long stock_id;
    private String stockName;
    private String stockCode;
    private String barcode;
    private String groupName;
    private String middleGroupName;
    private Integer unit;
    private String unitType;
    private BigDecimal salesPrice;
    private BigDecimal purchasePrice;

}
