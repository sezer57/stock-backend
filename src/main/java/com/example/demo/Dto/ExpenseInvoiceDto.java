package com.example.demo.Dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExpenseInvoiceDto {
    private Long clientId;
    private  List<Integer>  quantity;
    private  List<String>  quantity_type;
    private String autherized;
    private List<Long> stockCodes;
    private List<BigDecimal> price  ;
    private BigDecimal vat  ;
    private LocalDateTime  date;
}
