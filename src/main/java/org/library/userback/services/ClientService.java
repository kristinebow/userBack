package org.library.userback.services;

import org.library.userback.entity.AppUser;
import org.library.userback.entity.Client;
import org.library.userback.repository.ClientRepository;
import org.library.userback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    public Client saveClient(Client client, Long addedByUserId) {
        AppUser addedByUser = userRepository.findById(addedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + addedByUserId));

        client.setAddedBy(addedByUser);
        return clientRepository.save(client);
    }

    public List<Client> getClientsByAddedBy(Long addedByUserId) {
        AppUser addedByUser = userRepository.findById(addedByUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + addedByUserId));

        return clientRepository.findByAddedBy(addedByUser);
    }

    public Client updateClient(Client client, Long addedByUserId) {
        if (addedByUserId != null) {
            AppUser addedByUser = userRepository.findById(addedByUserId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + addedByUserId));

            client.setAddedBy(addedByUser);
            return clientRepository.save(client);
        } else {
            throw new RuntimeException("Added by user id is empty!");
        }
    }
}
