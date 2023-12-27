package com.example.demo.service;

import com.example.demo.Dto.BalanceTransferDto;
import com.example.demo.model.Balance;
import com.example.demo.model.BalanceTransfer;
import com.example.demo.model.WarehouseStock;
import com.example.demo.model.WarehouseTransfer;
import com.example.demo.repository.BalanceTransferRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class BalanceTransferService {
    private final BalanceTransferRepository balanceTransferRepository;
    private final BalanceService balanceService;

    public BalanceTransferService(BalanceTransferRepository balanceTransferRepository, BalanceService balanceService) {
        this.balanceTransferRepository = balanceTransferRepository;
        this.balanceService = balanceService;
    }

    public String transfer(BalanceTransferDto balanceTransferDto) {


        Balance sourceBalance = balanceService.findByBalance_balanceId(balanceTransferDto.getSystem());
        Balance targetBalance = balanceService.findByBalance_balanceId(balanceTransferDto.getClient());

        if(sourceBalance==null)
        {
            return "kaynak bulunamadı";

        } else if (targetBalance==null)
        {
            return "target bulunamadı";
        }

        BigDecimal oldQc=sourceBalance.getTurnoverCredit();
        BigDecimal oldQd=sourceBalance.getTurnoverDebit();

        BigDecimal oldTc=targetBalance.getTurnoverCredit();
        BigDecimal oldTd=targetBalance.getTurnoverDebit();

        System.out.println("oldQc:"+oldQc);
        System.out.println("oldQc:"+oldQd);

//        if(Objects.equals(oldQc, BigDecimal.valueOf(0))){
//            return "depoda yeterli ürün yok";
//        }
//        if(Objects.equals(oldQc, BigDecimal.valueOf(0))){
//            return "depoda yeterli remainin ürün yok";
//        }


        sourceBalance.setTurnoverCredit(oldQc.subtract(balanceTransferDto.getTurnoverCreditAmount()));
        sourceBalance.setTurnoverDebit(oldQd.subtract(balanceTransferDto.getTurnoverDebitAmount()));
        System.out.println(oldQc.subtract(balanceTransferDto.getTurnoverCreditAmount()));
        System.out.println(oldQd.subtract(balanceTransferDto.getTurnoverDebitAmount()));

        targetBalance.setTurnoverCredit(oldTc.add(balanceTransferDto.getTurnoverCreditAmount()));
        targetBalance.setTurnoverDebit(oldTd.add(balanceTransferDto.getTurnoverDebitAmount()));
        balanceService.savedb(sourceBalance);
        balanceService.savedb(targetBalance);
        System.out.println("oldQc:"+oldQc.subtract(oldQc.add(balanceTransferDto.getTurnoverCreditAmount())));
        System.out.println("oldQc:"+oldQd.subtract(oldQd.add(balanceTransferDto.getTurnoverDebitAmount())));


        BalanceTransfer balanceTransfer =new BalanceTransfer(
                sourceBalance,
                targetBalance,
                balanceTransferDto.getTurnoverDebitAmount(),
                balanceTransferDto.getTurnoverCreditAmount(),
                LocalDate.now().format(DateTimeFormatter.ofPattern("d/MM/uuuu"))+" " +LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        );
        balanceTransferRepository.save(balanceTransfer);

        return "from :"+ sourceBalance.getClientID() + " to:"+targetBalance.getClientID()+ " transfer success";
    }

}
