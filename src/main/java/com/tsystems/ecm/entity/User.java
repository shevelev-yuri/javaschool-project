package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String login;

    private String password;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}
