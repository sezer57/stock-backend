package com.example.demo.service;

import com.example.demo.model.Warehouse;
import com.example.demo.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return warehouseRepository.findAll();
    }
    public Warehouse getWarehouseWithId(Long id){
        return warehouseRepository.findWarehouseByWarehouseId(id);
    }
}
