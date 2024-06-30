package com.example.demo.service;

import com.example.demo.Dto.*;
import com.example.demo.model.Stock;
import com.example.demo.model.Warehouse;
import com.example.demo.model.WarehouseStock;
import com.example.demo.repository.StockRepository;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.repository.WarehouseStockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseStockRepository warehouseStockRepository;
    private final WarehouseStockService warehouseStockService;

    public StockService(StockRepository stockRepository, WarehouseRepository warehouseRepository, WarehouseStockRepository warehouseStockRepository, WarehouseStockService warehouseStockService) {
        this.stockRepository = stockRepository;
        this.warehouseRepository = warehouseRepository;
        this.warehouseStockRepository = warehouseStockRepository;
        this.warehouseStockService = warehouseStockService;
    }

    public boolean addStock(StockDto stock) {
        if(stock.getWarehouse_id().isEmpty()){
            return false;
        }
        else
        {
            List<Long> warehouseIds = stock.getWarehouse_id();
            for (Long warehouseId : warehouseIds) {
                try {
                    Long warehouseIdLong = Long.valueOf(warehouseId);
                    Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseIdLong);
                    if (warehouseOptional.isEmpty()) {
                        return false; // Warehouse bulunamadı, işlem başarısız
                    } else {
                        if (isDuplicateStock(warehouseId, stock.getStockName())) {
                            return false; // Aynı depoda aynı isimde stok var
                        }
                        Warehouse warehouse = warehouseOptional.get();
                        Stock newStock = new Stock(
                                stock.getUnit(),
                                stock.getStockCode(),
                                stock.getStockName(),
                                stock.getGroupName(),
                                stock.getMiddleGroupName(),
                                stock.getBarcode(),
                                stock.getSalesPrice(),
                                warehouse,
                                stock.getPurchasePrice(),
                                stock.getRegistrationDate()
                        );
                        stockRepository.save(newStock);
                        WarehouseStock warehouseStock = new WarehouseStock(warehouse, newStock);
                        warehouseStockRepository.save(warehouseStock);
                                           }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // Sayısal değere dönüştürmede hata, işlem başarısız
                    return false;
                }
            }
            return true;
        }
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }
    public Page<Stock> getStockWithIdProduct(Long warehouse_transfer_id,Pageable pageable) {
        return  stockRepository.findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalse(warehouse_transfer_id,pageable);
    }

    public Page<StockWarehouseDto> getStockWithId(Long warehouse_transfer_id, Pageable pageable) {
        List<Stock> stocks = stockRepository.findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalse(warehouse_transfer_id);

        List<StockWarehouseDto> stockWarehouseDtos = stocks.stream()
                .map(this::convertToStockWarehouseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(stockWarehouseDtos, pageable, stockWarehouseDtos.size());
    }
    public Page<StockWarehouseDto> getStocksByIdSearch(Long warehouse_transfer_id, Pageable pageable,String keyword) {
        Page<Stock> stocks = stockRepository.findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalseAndStockNameContaining(warehouse_transfer_id,pageable,keyword);

        List<StockWarehouseDto> stockWarehouseDtos = stocks.stream()
                .map(this::convertToStockWarehouseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(stockWarehouseDtos, pageable, stockWarehouseDtos.size());
    }

    private StockWarehouseDto convertToStockWarehouseDto(Stock stock) {
        return new StockWarehouseDto(
                stock.getStockId(),
                stock.getStockName(),
                stock.getSalesPrice(),
                stock.getPurchasePrice(),
                stock.getWarehouse().getWarehouseId(),
                warehouseStockService.findWarehouseStockQuantity(stock.getStockId()));
    }
    public Stock getstockjustid(Long id){
        return stockRepository.findStockByStockId(id);
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

    private boolean isDuplicateStock(Long warehouseId, String stockName) {

        return stockRepository.existsStocksByWarehouseWarehouseIdAndStockName(Long.valueOf(warehouseId.toString()), stockName);
    }

   // public boolean deleteStock(DeleteDto deleteDto) {
   //     Stock s = stockRepository.findStockByStockId(deleteDto.getId());
   //     stockRepository.deleteById(s.getStockId());
   //     return true;
   // }

    @Transactional
    public boolean deleteStock(Long stockId) {
        Stock stock = stockRepository.findStockByStockId(stockId);

        stock.setDeleted(true);
        stockRepository.save(stock);   return true;
    }

    public long getStockCode(){
        return stockRepository.count()+1;
    }

    public String getStocksRemainigById(Long warehouse_id) {
       return warehouseStockService.findByWarehouseStock_stockId(warehouse_id);

    }

    public Page<Stock> findAllActiveStocks(Pageable pageable) {
        return  stockRepository.findStocksByIsDeletedIsFalse(pageable);
    }

    public Page<Stock> searchItems(String keyword, Pageable pageable) {
        return stockRepository.findStocksByStockNameContainingAndIsDeletedIsFalse(keyword, pageable);
    }

    public Page<Stock> searchItemswithid(Long warehouse_id, Pageable pageable, String keyword) {
      return  stockRepository.findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalseAndStockNameContaining(warehouse_id,pageable,keyword);

    }
}
