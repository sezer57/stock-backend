package com.example.demo.repository;

import com.example.demo.model.ExpenseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseInvoiceRepository extends JpaRepository<ExpenseInvoice,Long> {
}
