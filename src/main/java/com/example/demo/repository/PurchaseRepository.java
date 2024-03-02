package com.example.demo.repository;

import com.example.demo.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
}
// burada eksik olabilir.