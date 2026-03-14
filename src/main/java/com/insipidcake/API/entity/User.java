package com.insipidcake.API.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String provider;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
