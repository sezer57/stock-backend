package com.example.demo.service;

import com.example.demo.model.ExpenseInvoice;
import com.example.demo.model.PurchaseInvoice;
import com.example.demo.model.WarehouseTransfer;
import com.example.demo.repository.ExpenseInvoiceRepository;
import com.example.demo.repository.PurchaseRepository;
import com.example.demo.repository.WarehouseTransferRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private  final ExpenseInvoiceRepository expenseInvoiceRepository;
    private final PurchaseRepository purchaseRepository;
    private final WarehouseTransferRepository warehouseTransferRepository;



    public ReportService(ExpenseInvoiceRepository expenseInvoiceRepository, PurchaseRepository purchaseRepository, WarehouseTransferRepository warehouseTransferRepository) {
        this.expenseInvoiceRepository = expenseInvoiceRepository;
        this.purchaseRepository = purchaseRepository;
        this.warehouseTransferRepository = warehouseTransferRepository;
    }

    // Constructor, Dependency Injection kullanılabilir

    public List<Object> getDailyExpenses(LocalDate date) {
        List<Object> dailyExpenses = new ArrayList<>();

        List<ExpenseInvoice> expenseInvoices = expenseInvoiceRepository.getExpenseInvoicesByDate(date);
        for (ExpenseInvoice expense : expenseInvoices) {
            Map<String, Object> expenseMap = new HashMap<>();
            expenseMap.put("expense_id", expense.getExpence_id());
            expenseMap.put("stockName", expense.getStockCode().getStockName());
            expenseMap.put("warehouseName", expense.getStockCode().getWarehouse().getName());
            expenseMap.put("clientName", expense.getClientId().getName());
            expenseMap.put("quantity", expense.getQuantity());
            expenseMap.put("price", expense.getPrice());
            expenseMap.put("date", expense.getDate());

            dailyExpenses.add(expenseMap);
        }

        List<PurchaseInvoice> purchaseInvoices = purchaseRepository.getPurchaseInvoicesByDate(date);
        for (PurchaseInvoice invoice : purchaseInvoices) {
            Map<String, Object> expenseMap = new HashMap<>();
            expenseMap.put("purchase_id", invoice.getPurchase_id());
            expenseMap.put("stockName", invoice.getStockCode().getStockName());
            expenseMap.put("warehouseName", invoice.getStockCode().getWarehouse().getName());
            expenseMap.put("clientName", invoice.getClientId().getName());
            expenseMap.put("quantity", invoice.getQuantity());
            expenseMap.put("price", invoice.getPrice());
            expenseMap.put("date", invoice.getDate());

            dailyExpenses.add(expenseMap);
        }


        List<WarehouseTransfer> warehouseTransfers = warehouseTransferRepository.getWarehouseTransfersByDate(date);
        for (WarehouseTransfer warehouse : warehouseTransfers) {
            Map<String, Object> expenseMap = new HashMap<>();
            expenseMap.put("warehousetransfer_id", warehouse.getWarehouseTransferId());
            expenseMap.put("approvalstatus", warehouse.getApprovalStatus());
            expenseMap.put("source", warehouse.getSource().getName());
            expenseMap.put("target", warehouse.getTarget().getName());
            expenseMap.put("quantity", warehouse.getQuantity());
            expenseMap.put("date", warehouse.getDate());
            expenseMap.put("comment", warehouse.getComment());

            dailyExpenses.add(expenseMap);
        }

        return dailyExpenses;
    }

    public List<Object> getWeeklyPurchaseInvoices(LocalDate startDate, LocalDate endDate) {
        List<Object> weekly = new ArrayList<>();

        List<ExpenseInvoice> expenseInvoices = expenseInvoiceRepository.getExpenseInvoicesByDateBetween(startDate,endDate);
        weekly.addAll(expenseInvoices);

        List<PurchaseInvoice> purchaseInvoices = purchaseRepository.getPurchaseInvoicesByDateBetween(startDate,endDate);
        weekly.addAll(purchaseInvoices);

        List<WarehouseTransfer> warehouseTransfers = warehouseTransferRepository.getWarehouseTransfersByDateBetween(startDate,endDate);
        weekly.addAll(warehouseTransfers);

        return weekly;
    }


}