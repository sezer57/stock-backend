package com.example.demo.service;

import com.example.demo.Dto.ClientDto;
import com.example.demo.model.Balance;
import com.example.demo.model.Client;
import com.example.demo.model.Stock;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
        return clientRepository.findClientByIsDeletedIsFalse();
    }


    public Client getClientWithName(String name){
        Client clients = clientRepository.findClientByName(name);
       return clients;
    }

    public Client getClientWithName2(String name){
        Client clients = (Client) clientRepository.findClientByName(name);
        return clients;
    }

    public long getClientCode(){
        return clientRepository.count()+1;
    }

    public boolean updateClient(Long clientId, ClientDto clientDto) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client existingClient = optionalClient.get();
            // Update existingClient fields with clientDto values
       //     existingClient.setClientCode(clientDto.getClientCode());
            existingClient.setCommercialTitle(clientDto.getCommercialTitle());
            existingClient.setName(clientDto.getName());
            existingClient.setSurname(clientDto.getSurname());
            existingClient.setAddress(clientDto.getAddress());
            existingClient.setCountry(clientDto.getCountry());
            existingClient.setCity(clientDto.getCity());
            existingClient.setPhone(clientDto.getPhone());
            existingClient.setGsm(clientDto.getGsm());
        //    existingClient.setRegistrationDate(clientDto.getRegistrationDate());

            // Save the updated client
            clientRepository.save(existingClient);
            return true;
        } else {
            return false; // Client with given ID not found
        }
    }
    @Transactional
    public boolean deleteClient(Long id) {
        Client client = clientRepository.findClientByClientId(id);

        client.setDeleted(true);
        clientRepository.save(client);   return true;
    }
}
