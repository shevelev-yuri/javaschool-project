package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.enums.Role;
import lombok.*;

/**
 * The user DTO class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto {

    /**
     * The id.
     */
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
    private Role role;

}
