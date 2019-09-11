package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.entity.User;

import java.util.List;


/**
 * This service provides logic for manipulations with users.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
public interface UserService {

    /**
     * Gets the list of all doctors.
     *
     * @return the {@code List} of all users with role {@code 'Doctor'} or empty {@code List}
     * if no doctors registered in application
     */
    List<UserDto> getAllDoctors();

    /**
     * Gets the user by login.
     *
     * @param login the user login
     * @return the user entity or null, if no user found with this {@code username}
     */
    User getUserByLogin(String login);

    /**
     * Registers new user.
     *
     * @param userDto the user DTO to be saved
     * @return the user entity or null, if user with same login already in use
     */
    User registerNewUser(UserDto userDto);
}
