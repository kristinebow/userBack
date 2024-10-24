package org.library.userback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String address;
    private String country;
    @ManyToOne
    @JoinColumn(name = "added_by", referencedColumnName = "id", nullable = false)
    private AppUser addedBy;
}
