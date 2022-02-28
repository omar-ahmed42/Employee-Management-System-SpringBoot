package com.omar_ahmed42.employeemanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data @Entity @AllArgsConstructor @NoArgsConstructor
public class User{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;

    @OneToOne
    private Employee employee;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;
}
