package com.example.demo.service.impl;

import com.example.demo.dto.AuthResponse;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.Role;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AppUserService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AppUserServiceImpl(
            AppUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AppUser register(String email, String password, String role) {

        userRepository.findByEmail(email).ifPresent(u -> {
            throw new BadRequestException("email must be unique");
        });

        Role userRole = Role.builder()
                .name(role)
                .build();

        AppUser user = AppUser.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(Collections.singleton(userRole))
                .active(true)
                .build();

        return userRepository.save(user);
    }

    @Override
    public AuthResponse login(String email, String password) {

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = jwtTokenProvider.createToken(user);

        return AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRoles().iterator().next().getName())
                .build();
    }
}
