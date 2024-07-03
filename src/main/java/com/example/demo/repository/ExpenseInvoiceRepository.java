package com.example.demo.repository;

import com.example.demo.model.ExpenseInvoice;
import com.example.demo.model.PurchaseInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseInvoiceRepository extends JpaRepository<ExpenseInvoice,Long> {
    List<ExpenseInvoice>  findExpenseInvoicesByClient_ClientId(Long id);
    @Query("SELECT e FROM ExpenseInvoice e WHERE e.date >= :startOfDay AND e.date <= :endOfDay")
    List<ExpenseInvoice> findExpenseInvoicesByDate( LocalDateTime startOfDay,   LocalDateTime endOfDay);

    List<ExpenseInvoice> getExpenseInvoicesByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    Page<ExpenseInvoice> findAllByClient_NameContaining(Pageable pageable,String stockName);
    @Query("SELECT c FROM ExpenseInvoice c WHERE LOWER(CONCAT(c.client.name, ' ', c.client.surname)) LIKE LOWER(CONCAT('%', :keyword, '%'))  ")
    Page<ExpenseInvoice> findWithNS(String keyword, Pageable pageable);

    // Page<ExpenseInvoice> findExpenseInvoicesByClient_NameContaining(String Name,Pageable pageable);
    Page<ExpenseInvoice> findExpenseInvoicesByClientId_ClientId(Long id, Pageable pageable);



}
