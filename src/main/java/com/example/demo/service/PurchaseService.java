package com.example.demo.service;

import com.example.demo.Dto.PurchaseDto;
import com.example.demo.Dto.PurchaseDto2;
import com.example.demo.model.PurchaseInvoice;
import com.example.demo.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final StockService stockService;
    private final ClientService clientService;
    private final BalanceService balanceService;
    private final WarehouseStockService warehouseStockService;
    public PurchaseService(PurchaseRepository purchaseRepository, StockService stockService, ClientService clientService, BalanceService balanceService, WarehouseStockService warehouseStockService) {
        this.purchaseRepository = purchaseRepository;
        this.stockService = stockService;
        this.clientService = clientService;
        this.balanceService = balanceService;
        this.warehouseStockService = warehouseStockService;
    }

    public boolean addPurchase(PurchaseDto purchase) {

        PurchaseInvoice p = new PurchaseInvoice(stockService.getstockjustid(purchase.getStockCode()),clientService.getClientWithId(purchase.getClientId()), purchase.getQuantity(), purchase.getDate(), purchase.getPrice());
        warehouseStockService.updateQuantityIn(purchase.getStockCode(), purchase.getQuantity());
     //   Balance b = balanceService.findBalanceByClientID(purchase.getClientId());
    //    BigDecimal oldB=b.getTransactionalBalance();
     //   b.setTransactionalBalance(oldB.add(purchase.getPrice()));
        purchaseRepository.save(p);
        return true;
    }

    public List<PurchaseDto2> getAllPurchases() {
        List<PurchaseInvoice> purchaseInvoices = purchaseRepository.findAll();
        return purchaseInvoices.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PurchaseDto2 convertToDTO(PurchaseInvoice purchases) {
        PurchaseDto2 dto = new PurchaseDto2();
        dto.setPurchase_id(purchases.getPurchase_id());
        dto.setStockId(purchases.getStockCode().getStockId());
        dto.setStockName(purchases.getStockCode().getStockName());
        dto.setPrice(purchases.getPrice());
        dto.setQuantity(purchases.getQuantity());
        dto.setDate(purchases.getDate());
        dto.setClientName(purchases.getClientId().getName()+" "+purchases.getClientId().getSurname());
        dto.setClientAdress(purchases.getClientId().getAddress());
        dto.setClientPhone(purchases.getClientId().getPhone());
        return dto;
    }
    public List<PurchaseDto2> getPurchaseWithId(Long id) {
        List<PurchaseInvoice> purchaseInvoices = purchaseRepository.findPurchaseInvoicesByClientId_ClientId(id);
        return purchaseInvoices.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


}
