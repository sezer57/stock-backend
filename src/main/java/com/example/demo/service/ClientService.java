package com.example.demo.service;

import com.example.demo.Dto.ClientDto;
import com.example.demo.model.Balance;
import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final BalanceService balanceService;

    public ClientService(ClientRepository clientRepository, BalanceService balanceService){
       this.clientRepository = clientRepository;

        this.balanceService = balanceService;
    }

   public boolean addClient(ClientDto client){
       Client c = new Client(client.getClientCode(),client.getCommercialTitle(),client.getName(),client.getSurname(),client.getAddress(),client.getCountry(),client.getCity(),client.getPhone(),client.getGsm(),client.getRegistrationDate());
       clientRepository.save(c);
       Balance b = new Balance(c.getClientId(),"open", BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0),BigDecimal.valueOf(0)," ");
       balanceService.savedb(b);
       return true;
   }
    public Client getClientWithId(Long id){
        return clientRepository.findClientByClientId(id);
    }
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }


    public Client getClientWithName(String name){
        Client clients = clientRepository.findClientByName(name);
       return clients;
    }

    public Client getClientWithName2(String name){
        Client clients = (Client) clientRepository.findClientByName(name);
        return clients;
    }


}
