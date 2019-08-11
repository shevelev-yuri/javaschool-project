package com.tsystems.ecm.dto;

import com.tsystems.ecm.entity.UserEntity;
import com.tsystems.ecm.entity.enums.Role;

public class UserDto {

    private long id;

    private String login;

    private String name;

    private Role role;

    public UserDto() {
    }

    public UserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.login = userEntity.getLogin();
        this.name = userEntity.getName();
        this.role = userEntity.getRole();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
