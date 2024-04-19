package com.example.demo.service;


import com.example.demo.Dto.BalanceDto;
import com.example.demo.model.Balance;
import com.example.demo.model.Client;
import com.example.demo.repository.BalanceRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final ClientRepository clientRepository;

    public BalanceService(BalanceRepository balanceRepository, ClientRepository clientRepository) {
        this.balanceRepository = balanceRepository;
        this.clientRepository = clientRepository;


    }

    public boolean addBalance(BalanceDto balance, String name) {
        Client clients = clientRepository.findClientByName(name);
        if (isDuplicateClientID(balance.getClientID())) {
            return false;
        } else {
            Balance b = new Balance(balance.getClientID(), balance.getDebitCreditStatus(), balance.getDebit(), balance.getCredit(), balance.getCash(),
                    balance.getBalance(), balance.getComment());
            if (b.getClientID().equals(clients.getClientId())) {
                balanceRepository.save(b);
                return true;
            } else {
                return false;
            }
        }
    }

    public Balance findByBalance_balanceId(Long balanceId) {
        return balanceRepository.findByBalanceID(balanceId);
    }

    public void savedb(Balance balance) {
        balanceRepository.save(balance);
    }

    public List<Balance> getAllBalances() {
        return balanceRepository.findAll();
    }

    private boolean isDuplicateClientID(Long client_id) {
        return balanceRepository.existsBalancesByClientID(client_id);
    }

    public Balance findBalanceByClientID(Long ClientID) {
        return balanceRepository.findBalanceByClientID(ClientID);
    }

    public boolean updateBalance(Long clientID, String paymentType, BigDecimal Value) {
        Balance balance = balanceRepository.findBalanceByClientID(clientID);
        BigDecimal oldValue;
        switch (paymentType) {
            case "Debit":
                oldValue = balance.getDebit();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                BigDecimal newValue = oldValue.subtract(Value);
                balance.setDebit(newValue);
                break;

            case "Credit":
                oldValue = balance.getCredit();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                newValue = oldValue.subtract(Value);
                balance.setCredit(newValue);
                break;
            case "Balance":
                oldValue = balance.getBalance();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                newValue = oldValue.subtract(Value);
                balance.setBalance(newValue);
                break;
            case "Cash":
                oldValue = balance.getCash();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                newValue = oldValue.subtract(Value);
                balance.setCash(newValue);
                break;
            default:
                // Handle invalid type selection
                return false;
        }
        balanceRepository.save(balance);
        return true;

    }

    public boolean updateBalanceToSale(Long clientID, BigDecimal Value) {
        Balance balance = balanceRepository.findBalanceByClientID(clientID);
        BigDecimal oldValue;

        oldValue = balance.getBalance();
        if (oldValue == null) {
            oldValue = BigDecimal.ZERO;
        }
        BigDecimal newValue = oldValue.add(Value);
        balance.setBalance(newValue);

        balanceRepository.save(balance);
        return true;
    }
}

