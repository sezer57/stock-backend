package com.example.demo.service;

import com.example.demo.Dto.ClientDto;
import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
       this.clientRepository = clientRepository;

   }

   public boolean addClient(ClientDto client){
    Optional<Client> p = clientRepository.findById(Long.valueOf(client.getClientId()));
    if(p.isEmpty()){
        return false;
    }else{
    if(isDuplicateClient(client.getClientId())) {
        return false;
    }

    Client c = new Client(client.getClientId(),client.getRegistrationDate(),client.getClientCode(),client.getCommercialTitle(),client.getName(),client.getSurname(),client.getAddress(),client.getCountry(),client.getCity(),client.getPhone(),client.getGsm());
    clientRepository.save(c);
    return true;
    }
   }

   private boolean isDuplicateClient( Long clientId){
        return clientRepository.existsClientByClientId(Long.valueOf(clientId));
   }
}
