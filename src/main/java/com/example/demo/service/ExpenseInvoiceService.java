package com.example.demo.service;

import com.example.demo.Dto.ExpenseInvoiceDto;
import com.example.demo.Dto.ExpenseInvoiceDto2;
import com.example.demo.model.*;
import com.example.demo.repository.ExpenseInvoiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseInvoiceService {

    private final ExpenseInvoiceRepository expenseInvoiceRepository;
    private final StockService stockService;
    private final ClientService clientService;
    private final BalanceService balanceService;
    private final WarehouseStockService warehouseStockService;
    public ExpenseInvoiceService(ExpenseInvoiceRepository expenseInvoiceRepository, StockService stockService, ClientService clientService, BalanceService balanceService, WarehouseStockService warehouseStockService) {
        this.expenseInvoiceRepository = expenseInvoiceRepository;
        this.stockService = stockService;
        this.clientService = clientService;
        this.balanceService = balanceService;
        this.warehouseStockService = warehouseStockService;
    }
  public String addExpenseInvoice(ExpenseInvoiceDto expenseInvoice){
      List<Long> stockCodes = expenseInvoice.getStockCodes();
      List<Integer> quantities = expenseInvoice.getQuantity();
      List<BigDecimal> prices = expenseInvoice.getPrice();
      for (int i = 0; i < stockCodes.size(); i++) {
          Long stockCode = stockCodes.get(i);
          Integer quantity = quantities.get(i);
          BigDecimal price = prices.get(i);

          if (!warehouseStockService.updateQuantityOut(stockCode, quantity)) {
              return "not enough products in warehouse";
          }

          BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));

          balanceService.updateBalanceToSale(expenseInvoice.getClientId(), totalPrice);
      }
      List<Stock> stocks = new ArrayList<>();
      for (Long stockCode : stockCodes) {
          Stock stock = stockService.getstockjustid(stockCode);
          stocks.add(stock);
      }


      ExpenseInvoice e = new ExpenseInvoice(
              clientService.getClientWithId(expenseInvoice.getClientId()),
              new ArrayList<>(), // Initialize the list of invoices
              expenseInvoice.getDate()
      );

      List<Invoice> invoices = new ArrayList<>();
      for (int i = 0; i < stockCodes.size(); i++) {
          Stock stock = stockService.getstockjustid(stockCodes.get(i));
          Invoice invoice = new Invoice(stock, quantities.get(i), prices.get(i));
          invoice.setExpense(e); // Set the ExpenseInvoice object
          invoices.add(invoice);
      }

      e.setInvoices(invoices); // Set the list of invoices for ExpenseInvoice object

      expenseInvoiceRepository.save(e);
      return "success";

    }


    public List<ExpenseInvoiceDto2> getAllExpenseInvoice() {
        List<ExpenseInvoice> expenseInvoices = expenseInvoiceRepository.findAll();
        return expenseInvoices.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ExpenseInvoiceDto2 convertToDTO(ExpenseInvoice expenseInvoice) {
        ExpenseInvoiceDto2 dto = new ExpenseInvoiceDto2();
        dto.setExpense_id(expenseInvoice.getExpenseId());
        dto.setStockIds(expenseInvoice.getInvoices().stream()
                .map(invoice -> invoice.getStock().getStockId())
                .collect(Collectors.toList()));
        dto.setStockName(expenseInvoice.getInvoices().stream()
                .map(invoice -> invoice.getStock().getStockName())
                .collect(Collectors.toList()));
        dto.setPrice(expenseInvoice.getInvoices().stream()
                .map(invoice -> invoice.getPrice().toString()) // Convert BigDecimal to String
                .collect(Collectors.toList()));
        dto.setQuantity(expenseInvoice.getInvoices().stream()
                .map(invoice -> invoice.getQuantity().toString()) // Convert Integer to String
                .collect(Collectors.toList()));
        dto.setDate(expenseInvoice.getDate());
        dto.setClientName(expenseInvoice.getClient().getName()+" "+expenseInvoice.getClient().getSurname());
        dto.setClientAdress(expenseInvoice.getClient().getAddress());
        dto.setClientPhone(expenseInvoice.getClient().getPhone());
        return dto;
    }
    public List<ExpenseInvoiceDto2> getExpenseWithId(Long id) {
        List<ExpenseInvoice> purchaseInvoices =  expenseInvoiceRepository.findExpenseInvoicesByClient_ClientId(id);
        return purchaseInvoices.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}


