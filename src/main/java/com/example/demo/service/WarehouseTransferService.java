package com.example.demo.service;

import com.example.demo.Dto.WarehouseTransferDto;
import com.example.demo.repository.WarehouseTransferRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WarehouseTransferService {
    private final WarehouseTransferRepository warehouseTransferRepository;
   // private final WarehouseStockService warehouseStockService;

    public WarehouseTransferService(WarehouseTransferRepository warehouseTransferRepository) {
        this.warehouseTransferRepository = warehouseTransferRepository;
    }

//    public boolean transfer(WarehouseTransferDto warehouseTransferDto) {
//
//        warehouseStockService.findByWarehouseStock_stockId();
//        warehouseTransferDto.getSource_id();
//        warehouseTransferDto.getTarget_id();
//
//        return false;
//    }
}
