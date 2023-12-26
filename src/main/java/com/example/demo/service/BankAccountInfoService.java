package com.example.demo.service;

import com.example.demo.model.BankAccountInfo;
import com.example.demo.repository.BankAccountInfoRepository;
import org.springframework.stereotype.Service;
import com.example.demo.Dto.BankAccountInfoDto;

@Service
public class BankAccountInfoService {
    private final BankAccountInfoRepository bankAccountInfoRepository;

    public BankAccountInfoService(BankAccountInfoRepository bankAccountInfoRepository) {
        this.bankAccountInfoRepository = bankAccountInfoRepository;
    }

    public boolean addBankAccountInfo(BankAccountInfoDto bankAccountInfo ){
        BankAccountInfo b = new BankAccountInfo(bankAccountInfo.getClientId(),
                bankAccountInfo.getName(),
                bankAccountInfo.getCode(),
                bankAccountInfo.getAccountNumber(),
                bankAccountInfo.getIban());
        bankAccountInfoRepository.save(b);
        return true;
    }

}

