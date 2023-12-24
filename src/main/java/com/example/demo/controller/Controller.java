package com.example.demo.controller;

import com.example.demo.Dto.*;
import com.example.demo.model.Stock;
import com.example.demo.model.Warehouse;
import com.example.demo.model.WarehouseStock;
import com.example.demo.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {
    private final StockService stockService;
    private final WarehouseService warehouseService;
    private final WarehouseStockService warehouseStockService;
    private final WarehouseTransferService warehouseTransferService;
    private final ClientService clientService;

    private final InformationCodeService informationCodeService;
    public final BankAccountInfoService bankAccountInfoService;
    public Controller(StockService stockService, WarehouseService warehouseService, WarehouseStockService warehouseStockService, WarehouseTransferService warehouseTransferService, ClientService clientService, InformationCodeService informationCodeService, BankAccountInfoService bankAccountInfoService) {
        this.stockService = stockService;
        this.warehouseService = warehouseService;
        this.warehouseStockService = warehouseStockService;
        this.warehouseTransferService = warehouseTransferService;
        this.clientService = clientService;
        this.informationCodeService = informationCodeService;
        this.bankAccountInfoService = bankAccountInfoService;
    }

    // Stock ekleme
    @PostMapping("/stocks")
    public ResponseEntity<String> addStock(@RequestBody StockDto stock) {
        if (stockService.addStock(stock)) {
            return ResponseEntity.ok("Stock added successfully");
        } else {
            return ResponseEntity.ok("hata");
        }
    }

    // Warehouse ekleme
    @PostMapping("/warehouse")
    public ResponseEntity<String> addStock(@RequestBody Warehouse Warehouse) {
        warehouseService.addWarehouse(Warehouse);
        return ResponseEntity.ok("Stock added successfully");
    }

    //client ekleme

    @PostMapping("/clients")
    public ResponseEntity<String> addClient(@RequestBody ClientDto client){
      if(clientService.addClient(client)){
      return ResponseEntity.ok("Client added successfully");
      }else{
          return ResponseEntity.ok("Error of addition client");
      }
    }
    //InformationCode sorguları
    @PostMapping("/information-codes")
    public ResponseEntity<String> addInformationCode(@RequestBody InformationCodeDto informationCodeDto) {
        if (informationCodeService.addInformationCode(informationCodeDto)) {
            return ResponseEntity.ok("InformationCode updated successfully");
        } else {
            return ResponseEntity.ok("Error updating InformationCode");
        }
    }
@PostMapping("/bankAccountInfos")
public ResponseEntity<String> addBankAccountInfo (@RequestBody BankAccountInfoDto bankAccountInfo){
        if(bankAccountInfoService.addBankAccountInfo(bankAccountInfo)){
            return ResponseEntity.ok("InformationCode updated successfully");
        } else {
            return ResponseEntity.ok("Error updating InformationCode");
        }
}



    // Quantity ekleme
    @PatchMapping("/{stockId}/updateQuantityIn")
    public ResponseEntity<String> updateQuantityIn(@PathVariable Long stockId, @RequestParam Integer quantityIn) {

        if (warehouseStockService.updateQuantityIn(stockId, quantityIn)) {
            return ResponseEntity.status(HttpStatus.OK).body("WarehouseStock updateQuantityIn guncellendi:" + stockId);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hata updateQuantityIn:" + stockId);
    }

    // Quantity çıkarma
    @PatchMapping("/{stockId}/updateQuantityOut")
    public ResponseEntity<String> updateQuantityOut(@PathVariable Long stockId, @RequestParam Integer quantityOut) {

        if (warehouseStockService.updateQuantityOut(stockId, quantityOut)) {
            return ResponseEntity.status(HttpStatus.OK).body("WarehouseStock  updateQuantityOut guncellendi:" + stockId);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hata updateQuantityOut:" + stockId);
    }

    // bütün stocklar alma
    @GetMapping("/getStocks")
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();

        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(stocks);
        }
    }

    // bütün warehouseları alma
    @GetMapping("/getWarehouseStock")
    public ResponseEntity<List<WarehouseStock>> getAllWarehouseStock() {
        List<WarehouseStock> stocks = warehouseStockService.getAllWarehouseStock();

        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(stocks);
        }
    }

    //    warehouse stock tarnsfer
    @PostMapping("/warehouseStock/transfer")
    public ResponseEntity<String> transfer(@RequestBody WarehouseTransferDto warehouseTransferDto) {
        String transferResult = warehouseTransferService.transfer(warehouseTransferDto);

        if (transferResult != null) {
            // Transfer başarılıysa
            return ResponseEntity.status(HttpStatus.OK).body(transferResult);
        } else {
            // Transfer başarısızsa veya bir hata oluştuysa
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hata transfer");
        }


    }
}
