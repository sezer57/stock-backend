package com.example.demo.repository;

import com.example.demo.model.ExpenseInvoice;
import com.example.demo.model.WarehouseTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface WarehouseTransferRepository extends JpaRepository<WarehouseTransfer,Long> {
    List<WarehouseTransfer> findByApprovalStatus(String approvalStatus);
    @Query("SELECT e FROM WarehouseTransfer e WHERE e.date >= :startOfDay AND e.date <= :endOfDay")
    List<WarehouseTransfer> getWarehouseTransfersByDate( LocalDateTime startOfDay,   LocalDateTime endOfDay);
    List<WarehouseTransfer> getWarehouseTransfersByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
