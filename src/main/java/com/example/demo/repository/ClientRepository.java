package com.example.demo.repository;

import com.example.demo.model.Client;
import com.example.demo.model.PurchaseInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {

    Client findClientByClientId(Long id);
    Client findClientByName(String name);
    List<Client> getClientsByRegistrationDate(LocalDate date);
    List<Client> getClientsByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);
}
