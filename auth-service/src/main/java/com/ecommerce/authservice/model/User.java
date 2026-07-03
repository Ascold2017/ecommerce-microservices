package com.ecommerce.authservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity                          // говорит Hibernate: этот класс = строка в таблице
@Table(name = "users")           // имя таблицы (иначе Hibernate возьмёт имя класса)
@Getter
@Setter
@NoArgsConstructor               // JPA требует конструктор без аргументов
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.USER;

    @CreationTimestamp                               // Hibernate сам проставит время при вставке
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}