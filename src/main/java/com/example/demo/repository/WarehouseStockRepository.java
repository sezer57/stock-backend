package com.example.demo.repository;

import com.example.demo.model.Stock;
import com.example.demo.model.Warehouse;
import com.example.demo.model.WarehouseStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface WarehouseStockRepository extends JpaRepository<WarehouseStock,Long> {
  //  WarehouseStock findWarehouseStockByStockStockId(Long stockId);
  //  WarehouseStock findWarehouseStockByStockStockNameAndWarehouseWarehouseId(String stockId,Long warehouseId);
 //   WarehouseStock findWarehouseStockByWarehouseStockId(Long stockId);


    // Silinmemiş Stock'a ait WarehouseStock'ları getiren sorgu
    @Query("SELECT ws FROM WarehouseStock ws WHERE ws.stock.stockId = :stockId AND ws.stock.isDeleted = false")
    WarehouseStock findWarehouseStockByStockStockId(@Param("stockId") Long stockId);


    // Silinmemiş Stock adı ve Warehouse id'sine göre WarehouseStock'ı getiren sorgu
    @Query("SELECT ws FROM WarehouseStock ws WHERE ws.stock.stockName = :stockName AND ws.warehouse.warehouseId = :warehouseId AND ws.stock.isDeleted = false")
    WarehouseStock findWarehouseStockByStockStockNameAndWarehouseWarehouseId(@Param("stockName") String stockName, @Param("warehouseId") Long warehouseId);

    // Silinmemiş WarehouseStock'ı WarehouseStockId'ye göre getiren sorgu
    @Query("SELECT ws FROM WarehouseStock ws WHERE ws.warehouseStockId = :warehouseStockId AND ws.stock.isDeleted = false")
    WarehouseStock findWarehouseStockByWarehouseStockId(@Param("warehouseStockId") Long warehouseStockId);
    @Query("SELECT s FROM WarehouseStock s WHERE s.stock.isDeleted = false")
    Page<WarehouseStock> findAllActiveStocks(Pageable pageable);
    Page<WarehouseStock> findWarehouseStockByStockStockNameContainingAndWarehouseNameAndStockIsDeletedIsFalseOrStockBarcodeContainingAndWarehouseNameAndStockIsDeletedIsFalseOrStockStockCodeContainingAndWarehouseNameAndStockIsDeletedIsFalse(String keyword,String warehouseName,String keyword2,String warehouseName1,String keyword1,String warehouseName12, Pageable pageable);
 Page<WarehouseStock> findWarehouseStockByStockStockNameContainingAndStockIsDeletedIsFalse(String keyword, Pageable pageable);

    @Query("SELECT ws.stock.stockId FROM WarehouseStock ws")
    List<Long> findAllStockIds();




}
