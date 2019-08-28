package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserDtoMapper {

    public UserDto map(User src) {
        UserDto dst = new UserDto();

        dst.setId(src.getId());
        dst.setLogin(src.getLogin());
        dst.setName(src.getName());
        dst.setRole(src.getRole());

        return dst;
    }
}
