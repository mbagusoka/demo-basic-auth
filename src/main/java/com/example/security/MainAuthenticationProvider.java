package com.example.security;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MainAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(
        Authentication authentication
    ) throws AuthenticationException {

        // Authorization: Basic b2thQGdhbnRzLmNvbTpUdWthbmdzdXN1
        // Authorization: Basic, username oka@gants.com password Tukangsusu
        // Authorization -> (username -> name, password -> credentials)
        return userRepository
            .findByUsername(authentication.getName())
            .filter(user -> passwordEncoder.matches(((String) authentication.getCredentials()), user.getPassword()))
            .map(user -> new UsernamePasswordAuthenticationToken(
                    user,
                    authentication.getCredentials(),
                    Collections.emptyList()
                )
            )
            .orElseThrow(() -> new UsernameNotFoundException(
                String.format("Username %s not found", authentication.getName())
            ));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
