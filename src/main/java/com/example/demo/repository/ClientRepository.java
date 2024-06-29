package com.example.demo.repository;

import com.example.demo.model.Client;
import com.example.demo.model.PurchaseInvoice;
import com.example.demo.model.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {
    Page<Client> findClientByIsDeletedIsFalse(Pageable pageable);
    Client findClientByClientId(Long id);
    Client findClientByName(String name);
    List<Client> getClientsByRegistrationDate(LocalDate date);
    List<Client> getClientsByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT c FROM Client c WHERE LOWER(CONCAT(c.name, ' ', c.surname)) LIKE LOWER(CONCAT('%', :keyword, '%')) AND c.isDeleted = false")
    Page<Client> findsClientNameSurnameAndDeleted(String keyword,  Pageable pageable);
    //Page<Client> findClientsByNameContainingOrSurnameContainingAndIsDeletedIsFalse(String Name,String Surname, Pageable pageable);
}
