package com.example.demo.repository;

import com.example.demo.model.BankAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountInfoRepository extends JpaRepository<BankAccountInfo,Long> {
}
