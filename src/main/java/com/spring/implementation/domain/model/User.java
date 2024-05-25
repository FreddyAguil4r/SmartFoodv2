package com.spring.implementation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;
    @Column(name = "correo")
    private String email;
    @Column(name = "contrasenia")
    private String password;
    @Column(name = "nombre")
    private String firstName;
    @Column(name = "apellido")
    private String lastName;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
