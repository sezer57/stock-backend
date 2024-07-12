package com.example.demo.service;

import com.example.demo.Dto.WarehouseEditDto;
import com.example.demo.model.Stock;
import com.example.demo.model.Warehouse;
import com.example.demo.model.WarehouseStock;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.repository.WarehouseStockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final StockRepository stockRepository;
    private final WarehouseStockRepository warehouseStockRepository;

    public WarehouseService(WarehouseRepository warehouseRepository, StockRepository stockRepository, WarehouseStockRepository warehouseStockRepository) {
        this.warehouseRepository = warehouseRepository;
        this.stockRepository = stockRepository;
        this.warehouseStockRepository = warehouseStockRepository;
    }

//    public void addWarehouse(Warehouse warehouse) {
//        if(warehouseRepository.existsWarehouseByName(warehouse.getName())){
//            return;
//        }
//        warehouseRepository.save(warehouse);
//    }
public void addWarehouse(Warehouse warehouse) {
    try {
        if (warehouseRepository.existsWarehouseByName(warehouse.getName())) {
            return;
        }
        warehouseRepository.save(warehouse);
        List<Stock> stocks = stockRepository.findAll();
        List<String> warehouseStockCode = new ArrayList<>();

        for (Stock stock : stocks) {
            if (!warehouseStockCode.contains(stock.getStockCode())) {
                WarehouseStock warehouseStock = new WarehouseStock(warehouse, stock);
                warehouseStockRepository.save(warehouseStock);
                warehouseStockCode.add(stock.getStockCode());
            }
        }
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }
}





    public List<Warehouse> getWarehouse(){
        return warehouseRepository.findWarehouseByIsDeletedFalse();
    }
    public Warehouse getWarehouseWithId(Long id){
        return warehouseRepository.findWarehouseByWarehouseId(id);
    }

    public boolean updateWarehouse(Long id, WarehouseEditDto updatedWarehouse) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findWarehouseByWarehouseIdAndIsDeletedFalse(id);
        if (optionalWarehouse.isPresent()) {

            Warehouse warehouse = optionalWarehouse.get();
            warehouse.setName(updatedWarehouse.getName());
            warehouse.setAuthorized(updatedWarehouse.getAuthorized());
            warehouse.setPhone(updatedWarehouse.getPhone());
            warehouse.setAddress(updatedWarehouse.getAddress());
            warehouseRepository.save(warehouse);
            return true;
        } else {
            return false;
        }
    }
    @Transactional
    public boolean deleteWarehouse(Long id) {
        Warehouse warehouse = warehouseRepository.findWarehouseByWarehouseId(id);

        warehouse.setDeleted(true);
        warehouseRepository.save(warehouse);   return true;
    }
    public List<String> getWarehouseNames(){
        List<Warehouse> warehouses = warehouseRepository.findWarehouseByIsDeletedFalse();
        return warehouses.stream()
                .map(Warehouse::getName).collect(Collectors.toList());
    }

}
