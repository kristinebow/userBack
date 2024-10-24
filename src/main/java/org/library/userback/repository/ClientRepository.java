package org.library.userback.repository;

import org.library.userback.entity.AppUser;
import org.library.userback.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByAddedBy(AppUser addedBy);
}
