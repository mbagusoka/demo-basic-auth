package com.example.user.service;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.user.persistence.User;
import com.example.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(@Valid UserDto userDto) {
        validateUserExist(userDto.getUsername());
        User user = saveUser(userDto);

        return UserDto.fromUser(user);
    }

    @Override
    public boolean isUserValidLogin(UserDto userDto) {
        return userRepository
            .findByUsername(userDto.getUsername())
            .filter(user -> passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
            .isPresent();
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
            .map(UserDto::fromUser)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private User saveUser(UserDto userDto) {
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());

        return userRepository.save(userDto.toEntity(encryptedPassword));
    }

    private void validateUserExist(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException("Username is already exist");
        }
    }
}
