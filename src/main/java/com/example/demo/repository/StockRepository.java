package com.example.demo.repository;

import com.example.demo.Dto.DeleteDto;
import com.example.demo.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock,Long> {
    boolean existsStocksByWarehouseWarehouseIdAndStockName(Long warehouseId, String stockName);
    Stock findStockByStockId(Long id);
    List<Stock> findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalse(Long warehouseId) ;
    List<Stock> getStocksByRegistrationDate(LocalDate date);
    List<Stock> getStocksByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);

   // @Query("SELECT s FROM Stock s WHERE s.isDeleted = false")
  //  List<Stock> findAllActiveStocks();
    List<Stock> findStocksByIsDeletedIsFalse();

}
