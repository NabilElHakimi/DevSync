package org.example.DevSync1.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @Column(nullable = false)
    private int dailyTokens;

    @Column(nullable = false)
    private int monthUsed;

    private LocalDateTime updatedAt;

}