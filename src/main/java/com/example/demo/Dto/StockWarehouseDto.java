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
    public Integer quantity;
    public BigDecimal salesPrice;
    public StockWarehouseDto(Long stockId, String stockName,BigDecimal salesPrice, Long warehouseId, Integer quantity) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.salesPrice=salesPrice;
        this.warehouseId = warehouseId;
        this.quantity=quantity;
    }
}
