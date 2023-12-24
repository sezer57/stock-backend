package com.example.demo.service;

import com.example.demo.Dto.WarehouseTransferDto;
import com.example.demo.model.WarehouseStock;
import com.example.demo.model.WarehouseTransfer;
import com.example.demo.repository.WarehouseTransferRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WarehouseTransferService {
    private final WarehouseTransferRepository warehouseTransferRepository;
    private final WarehouseStockService warehouseStockService;

    public WarehouseTransferService(WarehouseTransferRepository warehouseTransferRepository, WarehouseStockService warehouseStockService) {
        this.warehouseTransferRepository = warehouseTransferRepository;
        this.warehouseStockService = warehouseStockService;
    }

    public String transfer(WarehouseTransferDto warehouseTransferDto) {
        if(Objects.equals(warehouseTransferDto.getSource_id(), warehouseTransferDto.getTarget_id())){
            return "target == source";
        }

        WarehouseStock sourceWarehouseStock = warehouseStockService.findByWarehouseStock_stockId(warehouseTransferDto.getSource_id());
        WarehouseStock targetWarehouseStock = warehouseStockService.findByWarehouseStock_stockId(warehouseTransferDto.getTarget_id());

        if(sourceWarehouseStock==null)
        {
            return "kaynak bulunamadı";
        } else if(!Objects.equals(sourceWarehouseStock.getWarehouse().getWarehouseId(), warehouseTransferDto.getStock_id())){
            return "kaynakta id:"+warehouseTransferDto.getStock_id()+ " ürün yok";
        } else if (targetWarehouseStock==null)
        {
            return "target bulunamadı";
        }

        Integer oldQt=sourceWarehouseStock.getQuantityTransfer();
        Integer oldQI=sourceWarehouseStock.getQuantityIn();
        Integer oldQR=sourceWarehouseStock.getQuantityRemaining();

        if(oldQI==0){
            return "depoda yeterli ürün yok";
        }
        if(oldQR==0){
            return "depoda yeterli remainin ürün yok";
        }


        sourceWarehouseStock.setQuantityRemaining(oldQR-warehouseTransferDto.getQuantity());
        sourceWarehouseStock.setQuantityTransfer(oldQt+warehouseTransferDto.getQuantity());
        warehouseStockService.savedb(sourceWarehouseStock);

        WarehouseTransfer newWarehouseTransfer= new WarehouseTransfer(
                sourceWarehouseStock.getWarehouse(),
                targetWarehouseStock.getWarehouse(),
                warehouseTransferDto.getStock_id(),
                warehouseTransferDto.getQuantity(),
                warehouseTransferDto.getDate(),
                warehouseTransferDto.getComment(),
                "Onay Bekliyor"
        );

        warehouseTransferRepository.save(newWarehouseTransfer);

        return "transfer başarılı";
    }
}
