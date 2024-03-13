package com.example.demo.service;

import com.example.demo.Dto.DeleteDto;
import com.example.demo.Dto.StockDto;
import com.example.demo.Dto.StockUpdateDto;
import com.example.demo.Dto.StockWarehouseDto;
import com.example.demo.model.Stock;
import com.example.demo.model.Warehouse;
import com.example.demo.model.WarehouseStock;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.repository.WarehouseStockRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseStockRepository warehouseStockRepository;

    public StockService(StockRepository stockRepository, WarehouseRepository warehouseRepository, WarehouseStockRepository warehouseStockRepository) {
        this.stockRepository = stockRepository;
        this.warehouseRepository = warehouseRepository;
        this.warehouseStockRepository = warehouseStockRepository;
    }

    public boolean addStock(StockDto stock) {
        Optional<Warehouse> p = warehouseRepository.findById(Long.valueOf(stock.getWarehouse_id()));
        if(p.isEmpty()){
            return false;
        }
        else {
            if (isDuplicateStock(stock.getWarehouse_id(), stock.getStockName())) {
                return false; //  Aynı depoda aynı isimde stok var
            }
            Warehouse w = p.get();
            Stock s = new Stock(stock.getUnit(),stock.getStockCode(),stock.getStockName(),stock.getGroupName(),stock.getMiddleGroupName(),stock.getBarcode(),stock.getSalesPrice(),w,stock.getPurchasePrice(),stock.getRegistrationDate());
            stockRepository.save(s);
            WarehouseStock ps = new WarehouseStock(w,s);
            warehouseStockRepository.save(ps);
            System.out.println(s.getRegistrationDate());
            return true;
        }
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public List<StockWarehouseDto> getStockWithId(Long warehouse_transfer_id) {
        List<Stock> stocks =  stockRepository.findStocksByWarehouse_WarehouseId(warehouse_transfer_id);

        if (stocks.isEmpty()) {
            return Collections.emptyList(); // Return empty list if no results found
        } else {
            return convertToStockWarehouseDto(stocks);
        }
    }
    public Stock getstockjustid(Long id){
        return stockRepository.findStockByStockId(id);
    }
    private List<StockWarehouseDto> convertToStockWarehouseDto(List<Stock> stocks) {
        return stocks.stream()
                .map(stock -> new StockWarehouseDto(
                        stock.getStockId(),
                        stock.getStockName(),
                        stock.getWarehouse().getWarehouseId()))
                .collect(Collectors.toList());
    }
    //stock update
    public boolean stockUpdate(StockUpdateDto stockUpdateDto){
        Stock s= stockRepository.findStockByStockId(stockUpdateDto.getStock_id());

        s.setBarcode(stockUpdateDto.getBarcode());
        s.setStockCode(stockUpdateDto.getStockCode());
        s.setGroupName(stockUpdateDto.getGroupName());
        s.setMiddleGroupName(stockUpdateDto.getGroupName());
        s.setStockName(stockUpdateDto.getStockName());
        s.setSalesPrice(stockUpdateDto.getSalesPrice());
        s.setUnit(stockUpdateDto.getUnit());
        s.setPurchasePrice(stockUpdateDto.getPurchasePrice());
        stockRepository.save(s);
        return true;
    }

    private boolean isDuplicateStock(Integer warehouseId, String stockName) {

        return stockRepository.existsStocksByWarehouseWarehouseIdAndStockName(Long.valueOf(warehouseId), stockName);
    }

    public boolean deleteStock(DeleteDto deleteDto) {
        Stock s = stockRepository.findStockByStockId(deleteDto.getId());

        stockRepository.deleteById(s.getStockId());
        return true;
    }
}
