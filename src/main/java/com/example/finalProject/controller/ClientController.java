package com.example.finalProject.controller;

import com.example.finalProject.exception.ClientNotFoundException;
import com.example.finalProject.model.Client;
import com.example.finalProject.repository.ClientRepository;
import com.example.finalProject.service.ClientService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@AllArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    private ClientRepository clientRepository;
    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Integer id) throws ClientNotFoundException {
        Client client = clientService.getClientById(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/add-client")
    public ResponseEntity<Client> createClient(@RequestBody @Valid Client client) {
        Client newClient = clientService.saveClient(client);
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@RequestBody Client newClient, @PathVariable("id") Integer id) {
        Client client = clientService.updateClient(newClient, id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteClient(@PathVariable("id") Integer id) {
        clientService.deleteClientById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
