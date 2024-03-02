package com.example.demo.service;

import com.example.demo.Dto.PurchaseDto;
import com.example.demo.model.Purchase;
import com.example.demo.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public boolean addPurchase(PurchaseDto purchase) {
        Purchase p = new Purchase(purchase.getPrice(), purchase.getBarcode(), purchase.getStockName(), purchase.getQuantity(), purchase.getUnit(), purchase.getDate(), purchase.getPrice());
        purchaseRepository.save(p);
        return true;
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }
}
