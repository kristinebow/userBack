package org.library.userback.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.userback.entity.Client;
import org.library.userback.services.ClientService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setId(1L);
        client.setFirstName("Test");
        client.setLastName("User");
    }

    @Test
    void testCreateClient() {
        Long addedBy = 1L;
        when(clientService.saveClient(any(Client.class), eq(addedBy))).thenReturn(client);

        ResponseEntity<Client> response = clientController.createClient(client, addedBy);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(client, response.getBody());
    }

    @Test
    void testGetClientsByAddedBy() {
        Long addedByUserId = 1L;
        List<Client> clients = Collections.singletonList(client);
        when(clientService.getClientsByAddedBy(addedByUserId)).thenReturn(clients);

        ResponseEntity<List<Client>> response = clientController.getClientsByAddedBy(addedByUserId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clients, response.getBody());
    }

    @Test
    void testUpdateClient() {
        Long addedBy = 1L;
        Long clientId = 1L;
        when(clientService.updateClient(any(Client.class), eq(addedBy))).thenReturn(client);

        ResponseEntity<Client> response = clientController.updateClient(clientId, client, addedBy);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
        verify(clientService).updateClient(client, addedBy);
    }
}