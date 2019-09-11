package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.entity.User;
import org.springframework.stereotype.Component;

/**
 * Mapper class
 */
@Component
public class UserEntityToUserDtoMapper {

    /**
     * Maps entity to DTO.
     *
     * @param src the source entity to map from
     * @return the DTO to map to
     */
    public UserDto map(User src) {
        UserDto dst = new UserDto();

        dst.setId(src.getId());
        dst.setLogin(src.getLogin());
        dst.setName(src.getName());
        dst.setRole(src.getRole());

        return dst;
    }
}
