package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseTransferDto {
    private Integer source_id;
    private Integer target_id;
    private Integer quantity;
    private String comment;
}
