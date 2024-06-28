package com.example.demo.service;

import com.example.demo.Dto.WarehouseEditDto;
import com.example.demo.model.Client;
import com.example.demo.model.Warehouse;
import com.example.demo.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public void addWarehouse(Warehouse warehouse) {
        if(warehouseRepository.existsWarehouseByName(warehouse.getName())){
            return;
        }
        warehouseRepository.save(warehouse);
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
