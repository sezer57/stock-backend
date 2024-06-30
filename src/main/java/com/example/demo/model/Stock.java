package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "stock_name", nullable = false)
    private String stockName;

    @Column(name = "stock_code", nullable = false)
    private String stockCode;

    @Column(name = "barcode", nullable = false)
    private String barcode;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "middle_group_name", nullable = false)
    private String middleGroupName;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "sales_price", nullable = false)
    private BigDecimal salesPrice;

    @Column(name = "purchase_price", nullable = false)
    private BigDecimal purchasePrice;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Stock() {}

    public Stock(String unit, String stockCode, String stockName, String groupName, String middleGroupName, String barcode, BigDecimal salesPrice, Warehouse p, BigDecimal purchasePrice, LocalDateTime registrationDate) {
        this.unit = unit;
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.groupName = groupName;
        this.middleGroupName = middleGroupName;
        this.barcode = barcode;
        this.salesPrice = salesPrice;
        this.warehouse = p;
        this.purchasePrice = purchasePrice;
        this.registrationDate = registrationDate;
        this.isDeleted = false;
    }
}
