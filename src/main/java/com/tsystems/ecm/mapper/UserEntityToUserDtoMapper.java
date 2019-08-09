package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserDtoMapper {

    public UserDto map(UserEntity src) {
        UserDto dst = new UserDto();

        dst.setId(src.getId());
        dst.setLogin(src.getLogin());
        dst.setName(src.getName());
        dst.setRole(src.getRole());

        return dst;
    }
}
