package com.example.demo.repository;

import com.example.demo.model.PurchaseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseInvoice,Long> {

    List<PurchaseInvoice> findPurchaseInvoicesByWarehouseId_WarehouseId(Long id);
    List<PurchaseInvoice> findPurchaseInvoicesByStockCodeStockId(Long id);
    List<PurchaseInvoice> getPurchaseInvoicesByDate(LocalDate date);
    List<PurchaseInvoice> getPurchaseInvoicesByDateBetween(LocalDate startDate, LocalDate endDate);
}
