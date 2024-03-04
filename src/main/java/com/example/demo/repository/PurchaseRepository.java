package com.example.demo.repository;

import com.example.demo.model.PurchaseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseInvoice,Long> {
}
// burada eksik olabilir.z