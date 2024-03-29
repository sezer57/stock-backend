package com.example.demo.repository;

import com.example.demo.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance,Long> {
    Balance findByBalanceID(Long balanceId);
    boolean existsBalancesByClientID(Long client_id);
    Balance findBalanceByClientID(Long ClientID);
}
