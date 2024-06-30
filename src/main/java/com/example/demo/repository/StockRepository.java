package com.example.demo.repository;

import com.example.demo.Dto.DeleteDto;
import com.example.demo.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock,Long> {
    boolean existsStocksByWarehouseWarehouseIdAndStockName(Long warehouseId, String stockName);
    Stock findStockByStockId(Long id);
    Page<Stock> findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalse(Long warehouseId,Pageable pageable) ;
    List<Stock> getStocksByRegistrationDate(LocalDateTime  date);
    List<Stock> getStocksByRegistrationDateBetween(LocalDateTime  startDate, LocalDateTime endDate);
    List<Stock> findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalse(Long warehouseId);
   // @Query("SELECT s FROM Stock s WHERE s.isDeleted = false")
  //  List<Stock> findAllActiveStocks();
  //  List<Stock> findStocksByIsDeletedIsFalse();
   Page<Stock> findStocksByIsDeletedIsFalse(Pageable pageable);
    Page<Stock> findStocksByStockNameContainingAndIsDeletedIsFalse(String stockName, Pageable pageable);

    Page<Stock> findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalseAndStockNameContaining(Long warehouseId, Pageable pageable,String stockName);
}
