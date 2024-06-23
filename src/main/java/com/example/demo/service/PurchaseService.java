package com.example.demo.service;

import com.example.demo.Dto.PurchaseDto;
import com.example.demo.Dto.PurchaseDto2;
import com.example.demo.model.*;
import com.example.demo.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final StockService stockService;
    private final ClientService clientService;
    private final BalanceService balanceService;
    private final WarehouseStockService warehouseStockService;
    private final WarehouseService warehouseService;
    public PurchaseService(PurchaseRepository purchaseRepository, StockService stockService, ClientService clientService, BalanceService balanceService, WarehouseStockService warehouseStockService, WarehouseService warehouseService) {
        this.purchaseRepository = purchaseRepository;
        this.stockService = stockService;
        this.clientService = clientService;
        this.balanceService = balanceService;
        this.warehouseStockService = warehouseStockService;

        this.warehouseService = warehouseService;
    }

    public boolean addPurchase(PurchaseDto purchase) {

        PurchaseInvoice p = new PurchaseInvoice(
                clientService.getClientWithId(purchase.getClientId()),
                purchase.getDate(),
                new ArrayList<>(),
                purchase.getAutherized()
        );
        List<Integer> quantities = purchase.getQuantity();
        List<BigDecimal> prices = purchase.getPrice();
        double vats = purchase.getVat().doubleValue();
        List<Long> stockCodes = purchase.getStockCode();
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        Balance b = balanceService.findBalanceByClientID(purchase.getClientId());

        for (int i = 0; i < prices.size(); i++) {
            double price = prices.get(i).doubleValue();
            // BigDecimal vatAmount = prices.get(i) * (1 + vatRate)

            totalPrice = BigDecimal.valueOf(price * (1 + vats/100));
           BigDecimal oldB=b.getBalance();

            b.setBalance(oldB.add(totalPrice));

        }

        for (int i = 0; i < stockCodes.size(); i++) {

            warehouseStockService.updateQuantityIn(stockCodes.get(i), quantities.get(i));

        }
        List<InvoiceP> invoices = new ArrayList<>();
        for (int i = 0; i < stockCodes.size(); i++) {
            double price = prices.get(i).doubleValue();
            Stock stock = stockService.getstockjustid(stockCodes.get(i));
            InvoiceP invoice = new InvoiceP(stock, quantities.get(i),BigDecimal.valueOf(price * (1 + vats/100)),vats);
            invoice.setPurchase(p); // Set the ExpenseInvoice object
            invoices.add(invoice);
        }
        p.setInvoices(invoices);



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
        dto.setStockId(purchases.getInvoices().stream()
                .map(invoice -> invoice.getStock().getStockId())
                .collect(Collectors.toList()));
        dto.setStockName(purchases.getInvoices().stream()
                .map(invoice -> invoice.getStock().getStockName())
                .collect(Collectors.toList()));
        dto.setPrice(purchases.getInvoices().stream()
                .map(InvoiceP::getPrice) // Convert BigDecimal to String
                .collect(Collectors.toList()));
        dto.setVat(purchases.getInvoices().stream()
                .map(InvoiceP::getVat) // Convert Integer to String
                .collect(Collectors.toList()));
        dto.setAutherized(purchases.getAutherized());
        dto.setQuantity(purchases.getInvoices().stream()
                .map(InvoiceP::getQuantity) // Convert Integer to String
                .collect(Collectors.toList()));
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
