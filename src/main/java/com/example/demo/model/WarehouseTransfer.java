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

    @Column(name = "stock_id")
    private Long stock_id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "transfer_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "approval_status")
    private String approvalStatus; // "Onay Bekliyor", "Onaylandı", "Reddedildi"


    public WarehouseTransfer() {

    }
    public WarehouseTransfer( Warehouse source, Warehouse target,Long stock_id, Integer quantity,LocalDateTime date, String comment, String approvalStatus) {
        this.stock_id=stock_id;
        this.source = source;
        this.target = target;
        this.quantity = quantity;
        this.comment = comment;
        this.date = date;
        this.approvalStatus = approvalStatus;
    }
}
