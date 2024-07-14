package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StockWarehouseDto {
    public Long stockId;
    public String stockName;
    public String stockCode;
    public String barcode;
    public Long warehouseId;
    public Double quantity;
    public Integer quantity_remaing;
    public String type;
    public Integer typeS;
    public BigDecimal salesPrice;
    public BigDecimal purchasePrice;
    public boolean statusStock;
    public StockWarehouseDto(Long stockId, String stockName,String stockCode,String barcode,BigDecimal salesPrice,BigDecimal purchasePrice, Long warehouseId, Double quantity,Integer quantity_remaing,String type,Integer typeS,boolean statusStock) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockCode = stockCode;
        this.barcode = barcode;
        this.salesPrice=salesPrice;
        this.purchasePrice=purchasePrice;
        this.warehouseId = warehouseId;
        this.quantity=quantity;
        this.quantity_remaing=quantity_remaing;
        this.type=type;
        this.typeS=typeS;
        this.statusStock=statusStock;
    }
}
