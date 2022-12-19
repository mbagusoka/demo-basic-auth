package com.example.user.service;

import java.util.List;

import javax.validation.Valid;

public interface UserService {

    UserDto registerUser(@Valid UserDto userDto);

    boolean isUserValidLogin(UserDto userDto);

    UserDto findById(Long id);

    List<UserDto> findAll();

    UserDtoList registerBulk(UserDtoList userDtos);
}
