package com.example.demo.repository;

import com.example.demo.model.WarehouseStock;
import org.springframework.data.jpa.repository.JpaRepository;



public interface WarehouseStockRepository extends JpaRepository<WarehouseStock,Long> {
    WarehouseStock findWarehouseStockByStockStockId(Long stockId);

}
