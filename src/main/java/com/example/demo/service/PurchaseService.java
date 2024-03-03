package com.example.demo.service;

import com.example.demo.Dto.PurchaseDto;
import com.example.demo.Dto.PurchaseDto2;
import com.example.demo.model.Purchase;
import com.example.demo.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final StockService stockService;
    private final ClientService clientService;
    public PurchaseService(PurchaseRepository purchaseRepository, StockService stockService, ClientService clientService) {
        this.purchaseRepository = purchaseRepository;
        this.stockService = stockService;
        this.clientService = clientService;
    }

    public boolean addPurchase(PurchaseDto purchase) {

        Purchase p = new Purchase(stockService.getstockjustid(purchase.getStockCode()),clientService.getClientWithId(purchase.getClientId()), purchase.getQuantity(), purchase.getDate(), purchase.getPrice());
        purchaseRepository.save(p);
        return true;
    }

    public List<PurchaseDto2> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();
        return purchases.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PurchaseDto2 convertToDTO(Purchase purchases) {
        PurchaseDto2 dto = new PurchaseDto2();
        dto.setPurchase_id(purchases.getPurchase_id());
        dto.setStockId(purchases.getStockCode().getStockId());
        dto.setStockName(purchases.getStockCode().getStockName());
        dto.setPrice(purchases.getPrice());
        dto.setQuantity(purchases.getQuantity());
        dto.setDate(purchases.getDate());
        return dto;
    }
}
