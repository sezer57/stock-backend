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
    public static boolean addInformationCode(InformationCodeDto InformationCodeDto informationCodeDto){

        InformationCode informationCode = new InformationCode(
                informationCode.getInfoCodeId(),
                informationCode.getDocumentNumber(),
                informationCode.getProcessType(),
                informationCode.getTransactionDate(),
                informationCode.getTransactionTime(),
                informationCode.getProcessAmount(),
                informationCode.getStatus()
        );
        informationCodeRepository.save(informationCode);

    }
}
