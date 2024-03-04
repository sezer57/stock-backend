package com.example.demo.service;

import com.example.demo.Dto.ExpenseInvoiceDto;
import com.example.demo.Dto.ExpenseInvoiceDto2;
import com.example.demo.Dto.PurchaseDto2;
import com.example.demo.model.ExpenseInvoice;
import com.example.demo.model.PurchaseInvoice;
import com.example.demo.repository.ExpenseInvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseInvoiceService {

    private final ExpenseInvoiceRepository expenseInvoiceRepository;
    private final StockService stockService;
    private final ClientService clientService;

    public ExpenseInvoiceService(ExpenseInvoiceRepository expenseInvoiceRepository, StockService stockService, ClientService clientService) {
        this.expenseInvoiceRepository = expenseInvoiceRepository;
        this.stockService = stockService;
        this.clientService = clientService;
    }
  public boolean addExpenseInvoice(ExpenseInvoiceDto expenseInvoice){

      ExpenseInvoice e = new ExpenseInvoice(stockService.getstockjustid(expenseInvoice.getStockCode()),clientService.getClientWithId(expenseInvoice.getClientId()), expenseInvoice.getQuantity(), expenseInvoice.getDate(), expenseInvoice.getPrice());
      expenseInvoiceRepository.save(e);
      return true;

    }


    public List<ExpenseInvoiceDto2> getAllExpenseInvoice() {
        List<ExpenseInvoice> expenseInvoices = expenseInvoiceRepository.findAll();
        return expenseInvoices.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ExpenseInvoiceDto2 convertToDTO(ExpenseInvoice expenseInvoice) {
        ExpenseInvoiceDto2 dto = new ExpenseInvoiceDto2();
        dto.setExpense_id(expenseInvoice.getExpence_id());
        dto.setStockId(expenseInvoice.getStockCode().getStockId());
        dto.setStockName(expenseInvoice.getStockCode().getStockName());
        dto.setPrice(expenseInvoice.getPrice());
        dto.setQuantity(expenseInvoice.getQuantity());
        dto.setDate(expenseInvoice.getDate());
        return dto;
    }
}


