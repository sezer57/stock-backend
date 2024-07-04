package com.example.demo.repository;

import com.example.demo.model.PurchaseInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseInvoice, Long> {

    Page<PurchaseInvoice> findPurchaseInvoicesByClientId_ClientId(Long id, Pageable pageable);

    List<PurchaseInvoice> findPurchaseInvoicesByClientId_ClientId(Long id);

    // List<PurchaseInvoice> findPurchaseInvoicesByStockCodeStockId(Long id);
    @Query("SELECT e FROM PurchaseInvoice e WHERE e.date >= :startOfDay AND e.date <= :endOfDay")
    List<PurchaseInvoice> getPurchaseInvoicesByDate(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<PurchaseInvoice> getPurchaseInvoicesByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT c FROM PurchaseInvoice c WHERE LOWER(CONCAT(c.clientId.name, ' ', c.clientId.surname)) LIKE LOWER(CONCAT('%', :keyword, '%'))  ")
    Page<PurchaseInvoice> findWithNS(String keyword, Pageable pageable);
    //Page<PurchaseInvoice> findPurchaseInvoicesByClientId_NameContaining(String keyword, Pageable pageable);
}
