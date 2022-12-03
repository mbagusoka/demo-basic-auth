package com.example.user.service;

import javax.validation.Valid;

public interface UserService {

    UserDto registerUser(@Valid UserDto userDto);

    boolean isUserValidLogin(UserDto userDto);

    UserDto findById(Long id);
}
