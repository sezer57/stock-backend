package com.example.demo.service;

import com.example.demo.Dto.ExpenseInvoiceDto;
import com.example.demo.Dto.ExpenseInvoiceDto2;
import com.example.demo.model.*;
import com.example.demo.repository.ExpenseInvoiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

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

    public ExpenseInvoiceDto2 addExpenseInvoice(ExpenseInvoiceDto expenseInvoice) {
        List<Long> stockCodes = expenseInvoice.getStockCodes();
        List<Integer> quantities = expenseInvoice.getQuantity();
        List<BigDecimal> prices = expenseInvoice.getPrice();
        List<String> quantities_type = expenseInvoice.getQuantity_type();
        double vats = expenseInvoice.getVat().doubleValue();
//      if(stockCodes.size()!=quantities.size()&&prices.size()!=quantities.size()&&stockCodes.size()!=prices.size()){
//          return "size error ";
//      }
        BigDecimal totalPrice = BigDecimal.valueOf(0);

        for (int i = 0; i < stockCodes.size(); i++) {


            double price = prices.get(i).doubleValue();
            // BigDecimal vatAmount = prices.get(i) * (1 + vatRate)

            totalPrice = BigDecimal.valueOf(price * (1 + vats / 100));
            Long stockCode = stockCodes.get(i);
            Integer quantity = quantities.get(i);


            if(quantities_type.get(i).equals("Carton")){
                //backende stock sayısını eğer carton olarak satıldıysa ona göre artırma

                warehouseStockService.updateQuantityOut(stockCode, (quantity*stockService.getQuantityTypeCount(stockCodes.get(i))));
            }
            else if(quantities_type.get(i).equals("Dozen")){
                warehouseStockService.updateQuantityOut(stockCode, quantity*12);

            }
            else {
                warehouseStockService.updateQuantityOut(stockCode, quantity);
            }

//          if (!warehouseStockService.updateQuantityOut(stockCode, quantity)) {
//              return "not enough products in warehouse";
//          }
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
                expenseInvoice.getDate(),
                expenseInvoice.getAutherized()

        );

        List<Invoice> invoices = new ArrayList<>();
        for (int i = 0; i < stockCodes.size(); i++) {
            double price = prices.get(i).doubleValue();
            Stock stock = stockService.getstockjustid(stockCodes.get(i));
            Invoice invoice = new Invoice(stock, quantities.get(i), BigDecimal.valueOf(price * (1 + vats / 100)), vats,quantities_type.get(i));
            invoice.setExpense(e); // Set the ExpenseInvoice object
            invoices.add(invoice);
        }

        e.setInvoices(invoices); // Set the list of invoices for ExpenseInvoice object

        expenseInvoiceRepository.save(e);
        return convertToDTO(e);

    }


    public Page<ExpenseInvoiceDto2> getAllExpenseInvoice(Pageable pageable, String keyword) {
        Page<ExpenseInvoice> expenseInvoices = expenseInvoiceRepository.findWithNS(keyword, pageable);
        List<ExpenseInvoiceDto2> expenseInvoiceDtos = expenseInvoices.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(expenseInvoiceDtos, pageable, expenseInvoices.getTotalElements());
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
        dto.setQuantity_type(expenseInvoice.getInvoices().stream()
                .map(Invoice::getQuantity_type)
                .collect(Collectors.toList()));
        dto.setVat(expenseInvoice.getInvoices().stream()
                .map(Invoice::getVat) // Convert Integer to String
                .collect(Collectors.toList()));
        dto.setDate(expenseInvoice.getDate());
        dto.setAutherized(expenseInvoice.getAutherized());
        dto.setClientName(expenseInvoice.getClient().getName() + " " + expenseInvoice.getClient().getSurname());
        dto.setClientAdress(expenseInvoice.getClient().getAddress());
        dto.setClientPhone(expenseInvoice.getClient().getPhone());
        return dto;
    }

    public Page<ExpenseInvoiceDto2> getExpenseWithId(Pageable pageable, String keyword, Long id) {
        Page<ExpenseInvoice> purchaseInvoices = expenseInvoiceRepository.findExpenseInvoicesByClient_ClientId(id, pageable);
        List<ExpenseInvoiceDto2> expenseInvoiceDto2s= purchaseInvoices.stream().map(this::convertToDTO).collect(Collectors.toList());

        return new PageImpl<>(expenseInvoiceDto2s, pageable, purchaseInvoices.getTotalElements());

    }
}


