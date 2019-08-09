package com.tsystems.ecm.service.impl;

import com.tsystems.ecm.dao.UserDao;
import com.tsystems.ecm.dto.UserDto;
import com.tsystems.ecm.entity.enums.Role;
import com.tsystems.ecm.entity.UserEntity;
import com.tsystems.ecm.mapper.UserEntityToUserDtoMapper;
import com.tsystems.ecm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private UserEntityToUserDtoMapper mapper;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserEntityToUserDtoMapper mapper) {
        this.userDao = userDao;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public List<UserDto> getAllDoctors() {
        List<UserEntity> entities = userDao.getAll();
        return entities.stream().filter(doctor -> doctor.getRole().equals(Role.DOCTOR)).map(mapper::map).collect(Collectors.toList());
    }

}
