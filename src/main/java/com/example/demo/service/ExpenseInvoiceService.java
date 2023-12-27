package com.example.demo.service;

import com.example.demo.Dto.ExpenseInvoiceDto;
import com.example.demo.model.ExpenseInvoice;
import com.example.demo.repository.ExpenseInvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseInvoiceService {

    private final ExpenseInvoiceRepository expenseInvoiceRepository;

    public ExpenseInvoiceService(ExpenseInvoiceRepository expenseInvoiceRepository) {
        this.expenseInvoiceRepository = expenseInvoiceRepository;
    }
  public boolean addExpenseInvoice(ExpenseInvoiceDto expenseInvoice){
        ExpenseInvoice e = new ExpenseInvoice(expenseInvoice.getClientID(),expenseInvoice.getBillNo(),expenseInvoice.getBillDate(),expenseInvoice.getDue(),expenseInvoice.getCustomCode(),expenseInvoice.getComment(),expenseInvoice.getTaxOffice(),expenseInvoice.getCommercialTitle(),expenseInvoice.getCurrencyAmount());
        expenseInvoiceRepository.save(e);
        return true;


    }
    public List<ExpenseInvoice> getAllExpenseInvoice(){
        return expenseInvoiceRepository.findAll();
    }

}


