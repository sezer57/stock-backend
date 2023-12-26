package com.example.demo.repository;

import com.example.demo.model.WarehouseTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseTransferRepository extends JpaRepository<WarehouseTransfer,Long> {
    List<WarehouseTransfer> findByApprovalStatus(String approvalStatus);
}
