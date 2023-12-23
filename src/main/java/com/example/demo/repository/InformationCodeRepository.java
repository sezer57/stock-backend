package com.example.demo.repository;

import com.example.demo.model.InformationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationCodeRepository extends JpaRepository<InformationCode,Long> {

    // boolean existsInformationCodeByınfo_Code_Id(Long clientId);

}
