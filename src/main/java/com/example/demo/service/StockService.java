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

import java.util.List;
import java.util.Objects;
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

    public String addStock(StockDto stock) {
        if(stock.getWarehouse_id().isEmpty()){
            return "warehouse empty error";
        } else {
            List<Long> warehouseIds = stock.getWarehouse_id();
            for (Long warehouseId : warehouseIds) {
                try {
                    Long warehouseIdLong = Long.valueOf(warehouseId);
                    Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseIdLong);
                    if (warehouseOptional.isEmpty()) {
                        return "warehouse empty error"; // Warehouse bulunamadı, işlem başarısız
                    } else {
                        if (isDuplicateStock(warehouseId, stock.getStockCode(), stock.getStockName(), stock.getBarcode())) {
                            return "same stockcode , stockname or barcode error"; // Aynı depoda aynı stok kodu, isim veya barkod ile stok var
                        }
                        Warehouse warehouse = warehouseOptional.get();
                        Stock newStock = new Stock(
                                stock.getUnitType(),
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
                    return "false";
                }
            }
            return "Succes";
        }
    }
    private boolean isDuplicateStock(Long warehouseId, String stockName) {

        return stockRepository.existsStocksByWarehouseWarehouseIdAndStockName(Long.valueOf(warehouseId.toString()), stockName);
    }
    private boolean isDuplicateStock(Long warehouseId, String stockCode, String stockName, String barcode) {
        return stockRepository.existsStocksByWarehouseWarehouseIdAndStockCode(warehouseId, stockCode) ||
                stockRepository.existsStocksByWarehouseWarehouseIdAndStockName(warehouseId, stockName) ||
                stockRepository.existsStocksByWarehouseWarehouseIdAndBarcode(warehouseId, barcode);
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }
    public Page<Stock> getStockWithIdProduct(Long warehouse_transfer_id,Pageable pageable) {
        return  stockRepository.findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalse(warehouse_transfer_id,pageable);
    }

    public Page<StockWarehouseDto> getStockWithId(Long warehouse_transfer_id, Pageable pageable) {
        Page<Stock> stocks = stockRepository.findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalse(warehouse_transfer_id, pageable);

        List<StockWarehouseDto> stockWarehouseDtos = stocks.stream()
                .map(this::convertToStockWarehouseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(stockWarehouseDtos, pageable, stocks.getTotalElements());
    }

    public Page<StockWarehouseDto> getStocksByIdSearch(Long warehouse_transfer_id, Pageable pageable, String keyword) {
        System.out.println(warehouse_transfer_id);
        Page<Stock> stocks = stockRepository.findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalseAndStockCodeContainingOrWarehouse_WarehouseIdAndIsDeletedIsFalseAndStockNameContainingOrWarehouse_WarehouseIdAndIsDeletedIsFalseAndBarcodeContaining(
                warehouse_transfer_id,keyword,warehouse_transfer_id,keyword,warehouse_transfer_id,keyword,pageable );

        List<StockWarehouseDto> stockWarehouseDtos = stocks.stream()
                .map(this::convertToStockWarehouseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(stockWarehouseDtos, pageable, stocks.getTotalElements());
    }

    private StockWarehouseDto convertToStockWarehouseDto(Stock stock) {
        double quantity = 0;
        Integer quantity_remaining = 0;

        if (Objects.equals(stock.getUnitType(), "Carton")) {///burda cartonun kaç olduğunu belirliyor
            quantity_remaining = warehouseStockService.findWarehouseStockQuantity(stock.getStockId());
            quantity = (double) quantity_remaining / stock.getUnit();
        }
        else if (Objects.equals(stock.getUnitType(), "Piece")) {System.out.println(stock.getUnitType()); System.out.println(stock.getUnit());
            quantity_remaining = warehouseStockService.findWarehouseStockQuantity(stock.getStockId());
            quantity = 0 ;
        }
        else if (Objects.equals(stock.getUnitType(), "Dozen")) {
            quantity_remaining = warehouseStockService.findWarehouseStockQuantity(stock.getStockId());
            quantity = (double) quantity_remaining / 12;
        }
        return new StockWarehouseDto(
                stock.getStockId(),
                stock.getStockName(),
                stock.getStockCode(),
                stock.getBarcode(),
                stock.getSalesPrice(),
                stock.getPurchasePrice(),
                stock.getWarehouse().getWarehouseId(),
                quantity,
                quantity_remaining,
                stock.getUnitType(),
                stock.getUnit(),
                stock.isStatusStock()
        );
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
        s.setUnitType(stockUpdateDto.getUnitType());
        s.setPurchasePrice(stockUpdateDto.getPurchasePrice());
        stockRepository.save(s);
        return true;
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
        Long a= Long.valueOf("1");
        return stockRepository.countStocksByWarehouse_WarehouseId(a)+1;
    }

    public String getStocksRemainigById(Long warehouse_id) {
       return warehouseStockService.findByWarehouseStock_stockId(warehouse_id);

    }

    public Page<Stock> findAllActiveStocks(Pageable pageable) {
        return  stockRepository.findStocksByIsDeletedIsFalse(pageable);
    }

    public Page<Stock> searchItems(String keyword, Pageable pageable) {
        return stockRepository.findStocksByStockNameContainingOrBarcodeContainingOrStockCodeContainingAndIsDeletedIsFalse(keyword, keyword,keyword,pageable);
    }

    public Page<Stock> searchItemswithid(Long warehouse_id, Pageable pageable, String keyword) {
      return  stockRepository.findStocksByWarehouse_WarehouseIdAndIsDeletedIsFalseAndStockCodeContainingOrWarehouse_WarehouseIdAndIsDeletedIsFalseAndStockNameContainingOrWarehouse_WarehouseIdAndIsDeletedIsFalseAndBarcodeContaining( warehouse_id,keyword,warehouse_id,keyword, warehouse_id,keyword, pageable );

    }

    public Integer getQuantityTypeCount(Long stockid){
        return stockRepository.getStockUnitByStockId(stockid);
    }

    public boolean setStatus(Long stockId) {
        boolean status;
        Stock s = getstockjustid(stockId);
        status = s.isStatusStock();

        s.setStatusStock(!status);
        stockRepository.save(s);
         return !status;
    }
}
