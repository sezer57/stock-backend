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
            Balance b = new Balance(balance.getClientID(), balance.getDebitCreditStatus(), balance.getTurnoverDebit(), balance.getTurnoverCredit(), balance.getTurnoverBalance(), balance.getTransactionalDebit(), balance.getTransactionalCredit(), balance.getTransactionalBalance(), balance.getComment());
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
            case "turnoverDebit":
                System.out.println(paymentType);
                // Update turnoverDebit field
                oldValue = balance.getTurnoverDebit();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                BigDecimal newValue = oldValue.subtract(Value);
                balance.setTurnoverDebit(newValue);
                System.out.println("turnoverDebit güncellendi");
                System.out.println(oldValue);
                System.out.println(newValue);
                break;

            case "turnoverCredit":
                // Update turnoverCredit field
                oldValue = balance.getTurnoverCredit();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                newValue = oldValue.subtract(Value);
                balance.setTurnoverCredit(newValue);
                break;
            case "turnoverBalance":
                // Update transactionalDebit field
                oldValue = balance.getTurnoverBalance();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                newValue = oldValue.subtract(Value);
                balance.setTurnoverBalance(newValue);
                break;
            case "transactionalDebit":
                // Update transactionalDebit field
                oldValue = balance.getTransactionalDebit();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                newValue = oldValue.subtract(Value);
                balance.setTransactionalDebit(newValue);
                break;
            case "transactionalCredit":
                oldValue = balance.getTransactionalCredit();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                newValue = oldValue.subtract(Value);
                balance.setTransactionalCredit(newValue);
                break;
            case "transactionalBalance":
                oldValue = balance.getTransactionalBalance();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                newValue = oldValue.subtract(Value);
                balance.setTransactionalBalance(newValue);
                break;
            default:
                // Handle invalid type selection
                return false;
        }
        balanceRepository.save(balance);
        return false;

    }

}

