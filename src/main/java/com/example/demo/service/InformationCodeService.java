package com.example.demo.service;



import com.example.demo.Dto.InformationCodeDto;
import com.example.demo.model.InformationCode;
import com.example.demo.repository.InformationCodeRepository;
import org.springframework.stereotype.Service;

@Service
public class InformationCodeService {

    private final InformationCodeRepository informationCodeRepository;

    public InformationCodeService(InformationCodeRepository informationCodeRepository) {
        this.informationCodeRepository = informationCodeRepository;
    }
    public  boolean addInformationCode(InformationCodeDto  informationCodeDto){
        InformationCode informationCode = new InformationCode(
                informationCodeDto.getDocumentNumber(),
                informationCodeDto.getProcessType(),
                informationCodeDto.getTransactionDate(),
                informationCodeDto.getTransactionTime(),
                informationCodeDto.getProcessAmount(),
                informationCodeDto.getStatus()
        );
        informationCodeRepository.save(informationCode);
        return true;
    }
    public void add_db(InformationCode informationCode){
        informationCodeRepository.save(informationCode);
    }



}

