package com.example.demo.service;


import com.example.demo.model.InformationCode;
import com.example.demo.model.WarehouseStock;
import com.example.demo.repository.WarehouseStockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.util.List;

//time


@Service
public class WarehouseStockService {
    private final WarehouseStockRepository warehouseStockRepository;
    private final InformationCodeService informationCodeService;
    public WarehouseStockService(WarehouseStockRepository warehouseStockRepository, InformationCodeService informationCodeService) {
        this.warehouseStockRepository = warehouseStockRepository;
        this.informationCodeService = informationCodeService;
    }

    public boolean updateQuantityIn(Long stockId,Integer quantity_in) {
        WarehouseStock warehouseStock = warehouseStockRepository.findWarehouseStockByStockStockId(stockId);
        Integer oldQin=warehouseStock.getQuantityIn();
        Integer oldR=warehouseStock.getQuantityRemaining();
        if(oldQin==null){
            oldQin=0;
        }
        if(oldR==null){
            oldR=0;
        }
        warehouseStock.setQuantityIn(oldQin+quantity_in);
        warehouseStock.setQuantityRemaining(oldR+quantity_in);


        // add information update quantity

        InformationCode informationCode = new InformationCode();

        informationCode.setStatus("add to : "+warehouseStock.getStock().getStockName() +" quantity:" + quantity_in);
        informationCode.setProcessType("succes");
        informationCode.setTransactionTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        informationCode.setDocumentNumber(1);
        informationCode.setTransactionDate(LocalDate.now().format(DateTimeFormatter.ofPattern("d/MM/uuuu")));
        informationCode.setProcessAmount(Long.valueOf(quantity_in));
        informationCodeService.add_db(informationCode);
        //

        warehouseStockRepository.save(warehouseStock);
        return true;
    }
    public boolean updateQuantityOut(Long stockId,Integer quantity_out) {
        WarehouseStock warehouseStock = warehouseStockRepository.findWarehouseStockByStockStockId(stockId);
        Integer oldQout=warehouseStock.getQuantityOut();
        Integer oldR=warehouseStock.getQuantityRemaining();
        if(oldQout==null){
            oldQout=0;
        }
        if(oldR==null){
            oldR=0;
        }
        warehouseStock.setQuantityOut(oldQout+quantity_out);
        warehouseStock.setQuantityRemaining(oldR-quantity_out);
        warehouseStockRepository.save(warehouseStock);
        return true;

    }

    public List<WarehouseStock> getAllWarehouseStock() {
        return warehouseStockRepository.findAll();
    }



    public WarehouseStock findByWarehouseStock_stockId(Long stockId){
        return  warehouseStockRepository.findWarehouseStockByStockStockId(stockId);
    }
    public void savedb(WarehouseStock warehouseStock){
        warehouseStockRepository.save(warehouseStock);
    }
}
