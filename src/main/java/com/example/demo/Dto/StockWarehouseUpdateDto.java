package com.example.demo.Dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockWarehouseUpdateDto {
    private Long stockId;
    private Integer quantityIn;

    private Integer quantityOut;
    private Integer quantityTransfer;
    private Integer quantityRemaining;

    private Integer quantityReserved;

    private Integer quantityBlocked;
    private Integer usableQuantity;
}
