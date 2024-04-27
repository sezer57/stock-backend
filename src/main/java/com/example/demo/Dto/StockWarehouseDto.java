package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockWarehouseDto {
    public Long stockId;
    public String stockName;
    public Long warehouseId;
    public Integer quantity;

    public StockWarehouseDto(Long stockId, String stockName, Long warehouseId, Integer quantity) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.warehouseId = warehouseId;
        this.quantity=quantity;
    }
}
