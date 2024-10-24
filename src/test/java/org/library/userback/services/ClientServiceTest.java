package org.library.userback.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.library.userback.entity.AppUser;
import org.library.userback.entity.Client;
import org.library.userback.repository.ClientRepository;
import org.library.userback.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    private ClientService clientService;
    private ClientRepository clientRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        userRepository = mock(UserRepository.class);
        clientService = new ClientService(clientRepository, userRepository);
    }

    @Test
    void testSaveClient() {
        Client client = new Client();
        AppUser user = new AppUser();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(clientRepository.save(client)).thenReturn(client);

        Client savedClient = clientService.saveClient(client, 1L);

        assertEquals(client, savedClient);
        verify(clientRepository).save(client);
    }

    @Test
    void testSaveClientUserNotFound() {
        Client client = new Client();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clientService.saveClient(client, 1L);
        });

        assertEquals("User not found with id: 1", exception.getMessage());
    }

    @Test
    void testGetClientsByAddedBy() {
        AppUser user = new AppUser();
        user.setId(1L);
        Client client1 = new Client();
        Client client2 = new Client();
        List<Client> clients = Arrays.asList(client1, client2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(clientRepository.findByAddedBy(user)).thenReturn(clients);

        List<Client> result = clientService.getClientsByAddedBy(1L);

        assertEquals(clients, result);
    }

    @Test
    void testGetClientsByAddedByUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clientService.getClientsByAddedBy(1L);
        });

        assertEquals("User not found with id: 1", exception.getMessage());
    }

    @Test
    void testUpdateClient() {
        Client client = new Client();
        AppUser user = new AppUser();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(clientRepository.save(client)).thenReturn(client);

        Client updatedClient = clientService.updateClient(client, 1L);

        assertEquals(client, updatedClient);
        verify(clientRepository).save(client);
    }

    @Test
    void testUpdateClientUserNotFound() {
        Client client = new Client();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clientService.updateClient(client, 1L);
        });

        assertEquals("User not found with id: 1", exception.getMessage());
    }

    @Test
    void testUpdateClientAddedByUserIdEmpty() {
        Client client = new Client();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clientService.updateClient(client, null);
        });

        assertEquals("Added by user id is empty!", exception.getMessage());
    }
}