package com.example.demo.service;

import com.example.demo.Dto.BalanceTransferDto;
import com.example.demo.model.Balance;
import com.example.demo.model.BalanceTransfer;
import com.example.demo.model.Stock;
import com.example.demo.repository.BalanceTransferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class BalanceTransferService {
    private final BalanceTransferRepository balanceTransferRepository;
    private final BalanceService balanceService;

    public BalanceTransferService(BalanceTransferRepository balanceTransferRepository, BalanceService balanceService) {
        this.balanceTransferRepository = balanceTransferRepository;
        this.balanceService = balanceService;
    }

    public Page<BalanceTransfer> getAllBalanceTransfers(Pageable pageable, String keyword) {

        return balanceTransferRepository.findByKeyword(keyword,pageable);
    }

//    public String transfer(BalanceTransferDto balanceTransferDto) {
//
//
//        Balance sourceBalance = balanceService.findByBalance_balanceId(balanceTransferDto.getSystem());
//        Balance targetBalance = balanceService.findByBalance_balanceId(balanceTransferDto.getClient());
//
//        if(sourceBalance==null)
//        {
//            return "kaynak bulunamadı";
//
//        } else if (targetBalance==null)
//        {
//            return "target bulunamadı";
//        }
//
//        BigDecimal oldQc=sourceBalance.getCredit();
//        BigDecimal oldQd=sourceBalance.getDebit();
//
//        BigDecimal oldTc=targetBalance.getCredit();
//        BigDecimal oldTd=targetBalance.getDebit();
//
//
////        if(Objects.equals(oldQc, BigDecimal.valueOf(0))){
////            return "depoda yeterli ürün yok";
////        }
////        if(Objects.equals(oldQc, BigDecimal.valueOf(0))){
////            return "depoda yeterli remainin ürün yok";
////        }
//        sourceBalance.setCredit(oldQc.subtract(balanceTransferDto.getTurnoverCreditAmount()));
//        sourceBalance.setDebit(oldQd.subtract(balanceTransferDto.getTurnoverDebitAmount()));
//
//
//        targetBalance.setCredit(oldTc.add(balanceTransferDto.getTurnoverCreditAmount()));
//        targetBalance.setDebit(oldTd.add(balanceTransferDto.getTurnoverDebitAmount()));
//        balanceService.savedb(sourceBalance);
//        balanceService.savedb(targetBalance);
//
//
//        BalanceTransfer balanceTransfer =new BalanceTransfer(
//                sourceBalance,
//                targetBalance,
//                balanceTransferDto.getTurnoverDebitAmount(),
//                balanceTransferDto.getTurnoverCreditAmount(),
//                balanceTransferDto.getDate(),
//                balanceTransferDto.getComment()
//        );
//
//        balanceTransferRepository.save(balanceTransfer);
//
//        return "from :"+ sourceBalance.getClientID() + " to:"+targetBalance.getClientID()+ " transfer success";
//    }

}
