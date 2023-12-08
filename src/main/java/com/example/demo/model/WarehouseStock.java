package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
@Data
@Entity
@Table(name = "warehouse_stock")
public class WarehouseStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_stock_id")
    private Long warehouseStockId;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(name = "quantity_in")
    private Integer quantityIn;

    @Column(name = "quantity_out")
    private Integer quantityOut;

    @Column(name = "quantity_transfer")
    private Integer quantityTransfer;

    @Column(name = "quantity_remaining")
    private Integer quantityRemaining;

    @Column(name = "quantity_reserved")
    private Integer quantityReserved;

    @Column(name = "quantity_blocked")
    private Integer quantityBlocked;

    @Column(name = "usable_quantity")
    private Integer usableQuantity;

    // Getter and setters
    public WarehouseStock(){}
    public WarehouseStock( Warehouse warehouse,Stock stock){
        this.warehouse=warehouse;
        this.stock=stock;
        this.quantityIn = 0;
        this.quantityOut = 0;
        this.quantityTransfer = 0;
        this.quantityRemaining = 0;
        this.quantityReserved = 0;
        this.quantityBlocked = 0;
        this.usableQuantity = 0;
    }


}