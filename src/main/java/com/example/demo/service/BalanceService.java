package com.example.demo.service;


import com.example.demo.Dto.BalanceDto;
import com.example.demo.model.Balance;
import com.example.demo.model.BalanceTransfer;
import com.example.demo.model.Client;
import com.example.demo.repository.BalanceRepository;
import com.example.demo.repository.BalanceTransferRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final ClientRepository clientRepository;
    private final BalanceTransferRepository balanceTransferRepository;

    LocalDate currentDate = LocalDate.now();
    public BalanceService(BalanceRepository balanceRepository, ClientRepository clientRepository, BalanceTransferRepository balanceTransferRepository) {
        this.balanceRepository = balanceRepository;
        this.clientRepository = clientRepository;
        this.balanceTransferRepository = balanceTransferRepository;
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
        Client client = clientRepository.findClientByClientId(clientID);
        BigDecimal oldValue;

        oldValue = balance.getBalance();
        if (oldValue == null) {
            oldValue = BigDecimal.ZERO;
        }
        BigDecimal newValue = oldValue.subtract(Value);
        balance.setBalance(newValue);


        switch (paymentType) {
            case "Debit":
                oldValue = balance.getDebit();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                 newValue = oldValue.subtract(Value);
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
        String balanceType = "Purchase";
        BalanceTransfer balanceTransfer = new BalanceTransfer(
                balance.getClientID(),
                client.getName(),
                client.getSurname(),
                client.getCommercialTitle(),
                balance.getBalance(),
                Value,
                paymentType,
                currentDate,
                balanceType
                );
        balanceTransferRepository.save(balanceTransfer);
       // Payment p = new Payment(balance);
        return true;

    }
    public boolean updateBalance2(Long clientID, String paymentType, BigDecimal Value) {
        Balance balance = balanceRepository.findBalanceByClientID(clientID);
        Client client = clientRepository.findClientByClientId(clientID);
        BigDecimal oldValue;

        oldValue = balance.getBalance();
        if (oldValue == null) {
            oldValue = BigDecimal.ZERO;
        }
        BigDecimal newValue = oldValue.add(Value);
        balance.setBalance(newValue);


        switch (paymentType) {
            case "Debit":
                oldValue = balance.getDebit();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                newValue = oldValue.add(Value);
                balance.setDebit(newValue);
                break;

            case "Credit":
                oldValue = balance.getCredit();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                newValue = oldValue.add(Value);
                balance.setCredit(newValue);
                break;
            case "Cash":
                oldValue = balance.getCash();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                newValue = oldValue.add(Value);
                balance.setCash(newValue);
                break;
            default:
                // Handle invalid type selection
                return false;
        }
        balanceRepository.save(balance);
        // Payment p = new Payment(balance);
        String balanceType = "Sale";
        BalanceTransfer balanceTransfer = new BalanceTransfer(
                balance.getClientID(),
                client.getName(),
                client.getSurname(),
                client.getCommercialTitle(),
                balance.getBalance(),
                Value,
                paymentType,
                currentDate,
                balanceType
        );
        balanceTransferRepository.save(balanceTransfer);
        return true;

    }

    public boolean updateBalanceToSale(Long clientID, BigDecimal Value) {
        Balance balance = balanceRepository.findBalanceByClientID(clientID);
        BigDecimal oldValue;

        oldValue = balance.getBalance();
        if (oldValue == null) {
            oldValue = BigDecimal.ZERO;
        }
        BigDecimal newValue = oldValue.subtract(Value);
        balance.setBalance(newValue);

        balanceRepository.save(balance);
        return true;
    }
}

