package com.example.user.service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.user.persistence.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String username;

    @NotEmpty
    @Size(min = 8)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    static UserDto fromUser(User user) {
        return new UserDto(user.getId(), user.getName(), user.getUsername(), null);
    }

    User toEntity(String encryptedPassword) {
        return User.builder()
            .name(name)
            .username(username)
            .password(encryptedPassword)
            .build();
    }
}
