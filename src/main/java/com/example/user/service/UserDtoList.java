package com.example.user.service;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.example.user.persistence.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoList {

    private List<UserDto> users;

    static UserDtoList fromUsers(List<User> users) {
        List<UserDto> userDtos = users.stream()
            .map(UserDto::fromUser)
            .collect(Collectors.toList());

        return new UserDtoList(userDtos);
    }

    List<User> toUsers(UnaryOperator<String> encryptFunc) {
        return users.stream()
            .map(userDto -> userDto.toEntity(encryptFunc))
            .collect(Collectors.toList());
    }
}
