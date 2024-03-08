package com.example.demo.service;


import com.example.demo.Dto.BalanceDto;
import com.example.demo.model.Balance;
import com.example.demo.model.Client;
import com.example.demo.repository.BalanceRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final ClientRepository clientRepository;

    public BalanceService(BalanceRepository balanceRepository , ClientRepository clientRepository) {
        this.balanceRepository = balanceRepository;
        this.clientRepository = clientRepository;


    }

    public boolean addBalance(BalanceDto balance, String name){
        Client clients = clientRepository.findClientByName(name);

        if(isDuplicateClientID(balance.getClientID())){
            return false;
        }else {
            Balance b = new Balance(balance.getClientID(), balance.getDebitCreditStatus(), balance.getTurnoverDebit(), balance.getTurnoverCredit(), balance.getTurnoverBalance(), balance.getTransactionalDebit(), balance.getTransactionalCredit(), balance.getTransactionalBalance(), balance.getComment());
            if(b.getClientID().equals(clients.getClientId())) {
                balanceRepository.save(b);
                return true;
            }else {
                return false;
            }
        }
    }

    public Balance findByBalance_balanceId(Long balanceId){
        return  balanceRepository.findByBalanceID(balanceId);
    }
    public void savedb(Balance balance){
        balanceRepository.save(balance);
    }

    public List<Balance> getAllBalances(){
        return balanceRepository.findAll();
    }

    private boolean isDuplicateClientID(Long client_id){
        return balanceRepository.existsBalancesByClientID(client_id);
    }

}

