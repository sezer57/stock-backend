package com.example.demo.service;

import com.example.demo.Dto.WarehouseTransferDto;
import com.example.demo.model.Stock;
import com.example.demo.model.WarehouseStock;
import com.example.demo.model.WarehouseTransfer;
import com.example.demo.repository.WarehouseTransferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WarehouseTransferService {
    private final WarehouseTransferRepository warehouseTransferRepository;
    private final WarehouseStockService warehouseStockService;
    private final StockService stockService;
    public WarehouseTransferService(WarehouseTransferRepository warehouseTransferRepository, WarehouseStockService warehouseStockService, StockService stockService) {
        this.warehouseTransferRepository = warehouseTransferRepository;
        this.warehouseStockService = warehouseStockService;
        this.stockService = stockService;
    }

    public String transfer(WarehouseTransferDto warehouseTransferDto) {
        if(Objects.equals(warehouseTransferDto.getSource_id(), warehouseTransferDto.getTarget_id())){
            return "target == source";
        }
        Stock s = stockService.getstockjustid(warehouseTransferDto.getStock_id());

        WarehouseStock sourceWarehouseStock = warehouseStockService.findByWarehouseStock_name(s.getStockName(),warehouseTransferDto.getSource_id());
        WarehouseStock targetWarehouseStock = warehouseStockService.findByWarehouseStock_name(s.getStockName(),warehouseTransferDto.getTarget_id());//hatalı bulamıyor
        if(targetWarehouseStock==null){
            return "hedef depoya aynı ürünü ekleyin";
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

        return "transfer basarılı";
    }

    // onay bekleyen transferleri çekme
    public List<WarehouseTransfer> getallwaitingtransfer(){
        return warehouseTransferRepository.findByApprovalStatus("Onay Bekliyor");
    }

    public void change_status(Long warehouse_transfer_id,String status){
       WarehouseTransfer transfer = warehouseTransferRepository.getReferenceById(warehouse_transfer_id);
       Stock s = stockService.getstockjustid(transfer.getStock_id());
        if(Objects.equals(transfer.getApprovalStatus(), "Onay Bekliyor")){

        WarehouseStock sourceWarehouseStock = warehouseStockService.findByWarehouseStock_name(s.getStockName(),transfer.getSource().getWarehouseId());
        WarehouseStock targetWarehouseStock = warehouseStockService.findByWarehouseStock_name(s.getStockName(),transfer.getTarget().getWarehouseId());
        Integer add=transfer.getQuantity();

        if(Objects.equals(status, "onay")){
            Integer oldQR=targetWarehouseStock.getQuantityRemaining();
            Integer oldQR_s=sourceWarehouseStock.getQuantityTransfer();
            targetWarehouseStock.setQuantityIn(add);
            targetWarehouseStock.setQuantityRemaining(oldQR+add);
            sourceWarehouseStock.setQuantityTransfer(oldQR_s-add);
            sourceWarehouseStock.setQuantityOut(add);
            transfer.setApprovalStatus("onay");
        }
        else if(Objects.equals(status, "red")){
            Integer oldQT_s=sourceWarehouseStock.getQuantityTransfer();
            sourceWarehouseStock.setQuantityTransfer(oldQT_s-add);
            sourceWarehouseStock.setQuantityRemaining(oldQT_s+add);
            transfer.setApprovalStatus("red");
        }
        warehouseStockService.savedb(sourceWarehouseStock);
        warehouseTransferRepository.save(transfer);
    }

    }
}
