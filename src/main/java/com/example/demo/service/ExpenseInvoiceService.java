package com.example.demo.service;

import com.example.demo.Dto.ExpenseInvoiceDto;
import com.example.demo.Dto.ExpenseInvoiceDto2;
import com.example.demo.model.Balance;
import com.example.demo.model.ExpenseInvoice;
import com.example.demo.model.Stock;
import com.example.demo.model.WarehouseStock;
import com.example.demo.repository.ExpenseInvoiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
      if(!warehouseStockService.updateQuantityOut(expenseInvoice.getStockCode(), expenseInvoice.getQuantity())){
          return "quaintit remaning not exist";
      };
      ExpenseInvoice e = new ExpenseInvoice(
              stockService.getstockjustid(expenseInvoice.getStockCode()),
              clientService.getClientWithId(expenseInvoice.getClientId()),
              expenseInvoice.getQuantity(), expenseInvoice.getDate(),
              expenseInvoice.getPrice()
      );
      BigDecimal totalPrice = expenseInvoice.getPrice().multiply(BigDecimal.valueOf(expenseInvoice.getQuantity()));

      balanceService.updateBalanceToSale(expenseInvoice.getClientId(),totalPrice );
      //Balance b = balanceService.findBalanceByClientID(expenseInvoice.getClientId());
   //   BigDecimal oldB=b.getTransactionalBalance();
    //  b.setTransactionalBalance(oldB.subtract(expenseInvoice.getPrice()));
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
        dto.setExpense_id(expenseInvoice.getExpence_id());
        dto.setStockId(expenseInvoice.getStockCode().getStockId());
        dto.setStockName(expenseInvoice.getStockCode().getStockName());
        dto.setPrice(expenseInvoice.getPrice());
        dto.setQuantity(expenseInvoice.getQuantity());
        dto.setDate(expenseInvoice.getDate());
        dto.setClientName(expenseInvoice.getClientId().getName()+" "+expenseInvoice.getClientId().getSurname());
        dto.setClientAdress(expenseInvoice.getClientId().getAddress());
        dto.setClientPhone(expenseInvoice.getClientId().getPhone());
        return dto;
    }
    public List<ExpenseInvoiceDto2> getExpenseWithId(Long id) {
        List<ExpenseInvoice> purchaseInvoices =  expenseInvoiceRepository.findExpenseInvoicesByClientId_ClientId(id);
        return purchaseInvoices.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}


