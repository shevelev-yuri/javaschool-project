package com.tsystems.ecm.entity;

import com.tsystems.ecm.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * The user entity class.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

    /**
     * The id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * The user's login.
     */
    private String login;

    /**
     * The user's password.
     */
    private String password;

    /**
     * The user's name.
     */
    private String name;

    /**
     * The user's role.
     */
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
