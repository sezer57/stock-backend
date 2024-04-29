package com.example.demo.repository;

import com.example.demo.model.Client;
import com.example.demo.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Long> {
    boolean existsStocksByWarehouseWarehouseIdAndStockName(Long warehouseId, String stockName);
    Stock findStockByStockId(Long id);
    List<Stock> findStocksByWarehouse_WarehouseId(Long warehouseid);
    List<Stock> getStocksByRegistrationDate(LocalDate date);
    List<Stock> getStocksByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);
}
