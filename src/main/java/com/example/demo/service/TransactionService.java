package com.example.demo.service;

import com.example.demo.Dto.TransactionDto;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public boolean addTransaction(TransactionDto transaction){
        Transaction t = new Transaction(transaction.getDocumentNo(), transaction.getProcessType(),transaction.getTransactionDate(),transaction.getTransactionTime(),transaction.getProcessAmount());
        transactionRepository.save(t);
        return true;
    }

    public List<Transaction> getAllTransactions(){ return transactionRepository.findAll();}
}
