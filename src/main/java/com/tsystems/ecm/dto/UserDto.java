package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto {

    private long id;

    private String login;

    private String password;

    private String name;

    private Role role;

}
