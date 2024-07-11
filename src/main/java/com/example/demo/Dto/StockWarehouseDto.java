package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StockWarehouseDto {
    public Long stockId;
    public String stockName;
    public Long warehouseId;
    public Double quantity;
    public String type;
    public Integer typeS;
    public BigDecimal salesPrice;
    public BigDecimal purchasePrice;
    public StockWarehouseDto(Long stockId, String stockName,BigDecimal salesPrice,BigDecimal purchasePrice, Long warehouseId, Double quantity,String type,Integer typeS) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.salesPrice=salesPrice;
        this.purchasePrice=purchasePrice;
        this.warehouseId = warehouseId;
        this.quantity=quantity;
        this.type=type;
        this.typeS=typeS;
    }
}
