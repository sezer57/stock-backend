package com.example.demo.service;


import com.example.demo.Dto.DeleteDto;
import com.example.demo.Dto.StockWarehouseUpdateDto;
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
//        if(quantity_out>oldR){
//            return false;
//        }
        if(oldQout==null){
            oldQout=0;
        }
        if(oldR==null){
            oldR=0;
        }
        warehouseStock.setQuantityOut(oldQout+quantity_out);
        warehouseStock.setQuantityRemaining(oldR-quantity_out);

        // add information update quantity

        InformationCode informationCode = new InformationCode();

        informationCode.setStatus("out to : "+warehouseStock.getStock().getStockName() +" quantity:" + quantity_out);
        informationCode.setProcessType("succes");
        informationCode.setTransactionTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        informationCode.setDocumentNumber(1);
        informationCode.setTransactionDate(LocalDate.now().format(DateTimeFormatter.ofPattern("d/MM/uuuu")));
        informationCode.setProcessAmount(Long.valueOf(quantity_out));
        informationCodeService.add_db(informationCode);

        warehouseStockRepository.save(warehouseStock);
        return true;

    }
    public boolean updateWarehouseStock(StockWarehouseUpdateDto stockWarehouseUpdateDto){

        WarehouseStock warehouseStock = warehouseStockRepository.findWarehouseStockByStockStockId(stockWarehouseUpdateDto.getStockId());
        warehouseStock.setQuantityRemaining(stockWarehouseUpdateDto.getQuantityRemaining());
        warehouseStock.setQuantityTransfer(stockWarehouseUpdateDto.getQuantityTransfer());
        warehouseStock.setQuantityIn(stockWarehouseUpdateDto.getQuantityIn());
        warehouseStock.setQuantityOut(stockWarehouseUpdateDto.getQuantityOut());
        warehouseStock.setQuantityBlocked(stockWarehouseUpdateDto.getQuantityBlocked());
        warehouseStock.setQuantityReserved(stockWarehouseUpdateDto.getQuantityReserved());
        warehouseStock.setUsableQuantity(stockWarehouseUpdateDto.getUsableQuantity());
        warehouseStockRepository.save(warehouseStock);
        return true;
    }

    public boolean deleteWarehouseStock(DeleteDto deleteDto){

        WarehouseStock warehouseStock = warehouseStockRepository.findWarehouseStockByStockStockId(deleteDto.getId());
        warehouseStockRepository.delete(warehouseStock);
        return true;
    }


    public boolean updateStock(Long stockId,Integer quantity_out) {
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

        // add information update quantity

        InformationCode informationCode = new InformationCode();

        informationCode.setStatus("out to : "+warehouseStock.getStock().getStockName() +" quantity:" + quantity_out);
        informationCode.setProcessType("succes");
        informationCode.setTransactionTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        informationCode.setDocumentNumber(1);
        informationCode.setTransactionDate(LocalDate.now().format(DateTimeFormatter.ofPattern("d/MM/uuuu")));
        informationCode.setProcessAmount(Long.valueOf(quantity_out));
        informationCodeService.add_db(informationCode);
        //
        warehouseStockRepository.save(warehouseStock);
        return true;

    }
    public List<WarehouseStock> getAllWarehouseStock() {
        // 1.1 return warehouseStockRepository.findAll();
        return  warehouseStockRepository.findAllActiveStocks();
    }
    public List<WarehouseStock> getWithIdWarehouseStock(Long warehouse_id) {
        return (List<WarehouseStock>) warehouseStockRepository.findWarehouseStockByWarehouseStockId(warehouse_id);
    }

    public Integer findWarehouseStockQuantity(Long stockId){
        return  warehouseStockRepository.findWarehouseStockByStockStockId(stockId).getQuantityRemaining();
    }
    public String findByWarehouseStock_stockId(Long stockId){
        return  warehouseStockRepository.findWarehouseStockByStockStockId(stockId).getQuantityRemaining().toString();
    }
    public WarehouseStock findByWarehouseStock_name(String stockId,Long id){
        return  warehouseStockRepository.findWarehouseStockByStockStockNameAndWarehouseWarehouseId(stockId,id);
    }
    public void savedb(WarehouseStock warehouseStock){
        warehouseStockRepository.save(warehouseStock);
    }
}
