package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


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

    @OneToOne
    @JoinColumn(name = "source", nullable = false)
    private Warehouse source;

    @OneToOne
    @JoinColumn(name = "target", nullable = false)
    private Warehouse target;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "comment")
    private String comment;

    @Column(name = "approval_status")
    private String approvalStatus; // "Onay Bekliyor", "Onaylandı", "Reddedildi"
}
