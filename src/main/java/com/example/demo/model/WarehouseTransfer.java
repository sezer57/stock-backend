package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@Entity
@Table(name = "warehouse_transfer")
public class WarehouseTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_transfer_id")
    private Long warehouseTransferId;

    @ManyToOne
    @JoinColumn(name = "source", nullable = false)
    private Warehouse source;

    @ManyToOne
    @JoinColumn(name = "target", nullable = false)
    private Warehouse target;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "quantity_type")
    private String quantity_type;

    @Column(name = "stock_id")
    private Long stock_id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "transfer_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "approval_status")
    private String approvalStatus; // "Onay Bekliyor", "Onaylandı", "Reddedildi"

    @Column(name = "StockName")
    private String stockName;

    @Column(name = "StockCode")
    private String stockCode;





    public WarehouseTransfer() {

    }
    public WarehouseTransfer( Warehouse source, Warehouse target,Long stock_id, Integer quantity,String quantity_type,LocalDateTime date,
                              String comment, String approvalStatus, String stockName,String stockCode ) {
        this.stock_id=stock_id;
        this.source = source;
        this.target = target;
        this.quantity = quantity;
        this.quantity_type=quantity_type;
        this.comment = comment;
        this.date = date;
        this.approvalStatus = approvalStatus;
        this.stockName = stockName;
        this.stockCode = stockCode;
    }
}
