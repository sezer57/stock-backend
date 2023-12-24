package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class WarehouseTransferDto {
    private Long source_id;
    private Long target_id;
    private Long stock_id;
    private Integer quantity;
    private Date date;
    private String comment;
}
