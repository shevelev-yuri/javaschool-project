package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.UserDao;
import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.entity.User;
import com.tsystems.ecm.entity.enums.Role;
import com.tsystems.ecm.mapper.UserEntityToUserDtoMapper;
import com.tsystems.ecm.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LogManager.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    private UserEntityToUserDtoMapper mapper;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           UserEntityToUserDtoMapper mapper,
                           PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getAllDoctors() {
        List<User> entities = userDao.getAll();
        return entities.stream().filter(doctor -> doctor.getRole().equals(Role.DOCTOR)).map(mapper::map).collect(Collectors.toList());
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.getByLogin(login);
    }

    @Override
    public User registerNewUser(UserDto userDto) {
        if (loginExists(userDto.getLogin())) {
            //TODO handle existing user
            log.warn("The user with login '{}' already exists!", userDto.getLogin());

            return null;
        }

        final User user = new User();

        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setRole(userDto.getRole());
        userDao.save(user);

        return user;
    }

    private boolean loginExists(final String login) {
        return userDao.getByLogin(login) != null;
    }
}