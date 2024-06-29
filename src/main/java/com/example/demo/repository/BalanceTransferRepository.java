package com.example.demo.repository;

import com.example.demo.model.BalanceTransfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BalanceTransferRepository extends JpaRepository<BalanceTransfer,Long> {
   // Page<BalanceTransfer> findAllByClientNameContainingOrClientSurnameContaining(String keyword,Pageable pageable);
    @Query("SELECT c FROM BalanceTransfer c WHERE LOWER(CONCAT(c.clientName, ' ', c.clientSurname)) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<BalanceTransfer> findByKeyword(  String keyword, Pageable pageable);


}
