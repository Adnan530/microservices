package com.microservices.User.service;

import com.microservices.User.dto.UserDto;
import com.microservices.User.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    UserDto create(UserDto userDTO);
    List<UserDto> getAllUsers();
    Optional<UserDto> getById(Long id);
    User getUserById(Long id);
    UserDto update(Long id, UserDto userDTO);
    void delete(Long id);
}
