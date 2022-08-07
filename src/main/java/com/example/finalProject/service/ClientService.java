package com.example.finalProject.service;

import com.example.finalProject.exception.ClientNotFoundException;
import com.example.finalProject.model.Client;
import com.example.finalProject.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private static Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAllClients() {
        LOGGER.info("Getting all Clients");
        return clientRepository.findAll();
    }

    public Client saveClient(final Client client) {
        LOGGER.info("Saving Client to DB " + client);
        clientRepository.save(client);
        return client;
    }

    public Client getClientById(final Integer id) throws ClientNotFoundException {
        LOGGER.info("Getting Client information, where id: " + id);

        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not existing with id " + id));
    }

    public void deleteClientById(final Integer id) {
        LOGGER.warn("Deleting client where client id = " + id);
        clientRepository.deleteById(id);
    }

    public Client updateClient(final Client newClient, Integer id) {

        return clientRepository.findById(id)
                .map(client -> {
                    LOGGER.info("Updating client where ID = " + id);
                    client.setName(newClient.getName());
                    client.setPvmCode(newClient.getPvmCode());
                    client.setCompanyCode(newClient.getCompanyCode());
                    client.setAddress(newClient.getAddress());
                    client.setPhoneNumber(newClient.getPhoneNumber());
                    client.setEmail(newClient.getEmail());
                    return clientRepository.save(client);
                })
                .orElseGet(() -> {
                    LOGGER.info("Client not existing with id " + id + " Then creating new Client = " + newClient);
                    return clientRepository.save(newClient);
                });
    }


}
