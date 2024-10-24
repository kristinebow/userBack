package org.library.userback.controller;

import org.library.userback.entity.Client;
import org.library.userback.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client,
                                               @RequestParam Long addedBy) {
        Client savedClient = clientService.saveClient(client, addedBy);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }


    @GetMapping("/list/{addedByUserId}")
    public ResponseEntity<List<Client>> getClientsByAddedBy(@PathVariable Long addedByUserId) {
        List<Client> clients = clientService.getClientsByAddedBy(addedByUserId);
        return ResponseEntity.ok(clients);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client, @RequestParam Long addedBy) {
        client.setId(id);
        Client updatedClient = clientService.updateClient(client, addedBy);
        return ResponseEntity.ok(updatedClient);
    }
}
