package com.example.demo.repository;

import com.example.demo.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock,Long> {
    boolean existsStocksByWarehouseWarehouseIdAndStockCode(Long warehouseId, String stockName);
    boolean existsStocksByWarehouseWarehouseIdAndStockName(Long warehouseId, String stockName);
    boolean existsStocksByWarehouseWarehouseIdAndBarcode(Long warehouseId, String stockName);
    Integer countStocksByWarehouse_WarehouseId(Long warehouseId);
    @Query("SELECT s.unit FROM Stock s WHERE s.stockId = :stockId")
    Integer getStockUnitByStockId(Long stockId);
    Stock findStockByStockId(Long id);
    Page<Stock> findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalse(Long warehouseId,Pageable pageable) ;
    List<Stock> getStocksByRegistrationDate(LocalDateTime  date);
    List<Stock> getStocksByRegistrationDateBetween(LocalDateTime  startDate, LocalDateTime endDate);
 //   Page<Stock> findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalse(Long warehouseId,Pageable pageable);
   // @Query("SELECT s FROM Stock s WHERE s.isDeleted = false")
  //  List<Stock> findAllActiveStocks();
  //  List<Stock> findStocksByIsDeletedIsFalse();
   Page<Stock> findStocksByIsDeletedIsFalse(Pageable pageable);
    Page<Stock> findStocksByStockNameContainingOrBarcodeContainingOrStockCodeContainingAndIsDeletedIsFalse(String stockName,String stockName1,String stockName2, Pageable pageable);

    Page<Stock> findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalseAndStockCodeContainingOrWarehouse_WarehouseIdAndIsDeletedIsFalseAndStockNameContainingOrWarehouse_WarehouseIdAndIsDeletedIsFalseAndBarcodeContaining(Long warehouseId0, String s01,Long warehouseId1, String s1,Long warehouseId, String s21, Pageable pageable);
}
