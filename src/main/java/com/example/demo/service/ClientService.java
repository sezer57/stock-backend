package com.example.demo.service;

import com.example.demo.Dto.ClientDto;
import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
       this.clientRepository = clientRepository;

   }

   public boolean addClient(ClientDto client){
       Client c = new Client(client.getClientCode(),client.getCommercialTitle(),client.getName(),client.getSurname(),client.getAddress(),client.getCountry(),client.getCity(),client.getPhone(),client.getGsm(),client.getRegistrationDate());
       clientRepository.save(c);
       return true;
   }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }


}
