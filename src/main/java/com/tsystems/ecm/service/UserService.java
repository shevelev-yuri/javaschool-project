package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.entity.User;

import java.util.List;

public interface UserService {

    List<UserDto> getAllDoctors();

    User getUserByLogin(String username);

    User registerNewUser(UserDto userDto);
}
