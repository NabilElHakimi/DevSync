package org.example.DevSync1.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.DevSync1.enums.Role;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    public User() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL)
    private List<Task> tasks;


    @OneToOne
    private Token token;
    
}
