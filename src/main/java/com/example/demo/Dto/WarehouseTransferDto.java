package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class WarehouseTransferDto {
    private Long source_id;
    private Long target_id;
    private Long stock_id;
    private Integer quantity;
    private LocalDate date;
    private String comment;
}
