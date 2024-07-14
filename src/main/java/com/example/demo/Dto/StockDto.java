package com.example.demo.Dto;

import com.example.demo.model.Stock;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class StockDto {
    private LocalDateTime registrationDate;
    private String stockName;
    private String stockCode;
    private String barcode;
    private String groupName;
    private String middleGroupName;
    private Integer unit;
    private String unitType;
    private BigDecimal salesPrice;
    private BigDecimal purchasePrice;
    private List<Long> warehouse_id;

    public StockDto(LocalDateTime registrationDate, String stockName, String stockCode, String barcode, String groupName, String middleGroupName, Integer unit, String unitType, BigDecimal salesPrice, BigDecimal purchasePrice, List<Long> warehouse_id) {
        this.registrationDate = registrationDate;
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.barcode = barcode;
        this.groupName = groupName;
        this.middleGroupName = middleGroupName;
        this.unit = unit;
        this.unitType = unitType;
        this.salesPrice = salesPrice;
        this.purchasePrice = purchasePrice;
        this.warehouse_id = warehouse_id;
    }

    public static StockDto convert(Stock stock, List<Long> warehouse_id) {
        return  new StockDto(stock.getRegistrationDate(),
        stock.getStockName(),
        stock.getStockCode(),
        stock.getBarcode(),
        stock.getGroupName(),
        stock.getMiddleGroupName(),
        stock.getUnit(),
        stock.getUnitType(),
        stock.getSalesPrice(),
        stock.getPurchasePrice(),
                warehouse_id) ;
    }

}
