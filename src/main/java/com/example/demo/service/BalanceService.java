package com.example.demo.service;


import com.example.demo.Dto.BalanceDto;
import com.example.demo.model.Balance;
import com.example.demo.model.Client;
import com.example.demo.repository.BalanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public boolean addBalance(BalanceDto balance){
        if(isDuplicateClientID(balance.getClientID())){
            return false;
        }else {
            Balance b = new Balance(balance.getClientID(), balance.getDebitCreditStatus(), balance.getTurnoverDebit(), balance.getTurnoverCredit(), balance.getTurnoverBalance(), balance.getTransactionalDebit(), balance.getTransactionalCredit(), balance.getTransactionalBalance(), balance.getComment());
            balanceRepository.save(b);
            return true;
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

