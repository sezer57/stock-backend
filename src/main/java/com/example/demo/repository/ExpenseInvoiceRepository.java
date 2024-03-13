package com.example.demo.repository;

import com.example.demo.model.ExpenseInvoice;
import com.example.demo.model.PurchaseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseInvoiceRepository extends JpaRepository<ExpenseInvoice,Long> {
    List<ExpenseInvoice>  findExpenseInvoicesByClientId_ClientId(Long id);
    List<ExpenseInvoice>  getExpenseInvoicesByDate(LocalDate date);
    List<ExpenseInvoice> getExpenseInvoicesByDateBetween(LocalDate startDate, LocalDate endDate);
}
