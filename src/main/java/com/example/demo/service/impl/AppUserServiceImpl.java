package com.example.demo.service.impl;

import com.example.demo.entity.AppUser;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AppUserService;

import java.util.Optional;

public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // Constructor (simple, explicit)
    public AppUserServiceImpl(AppUserRepository userRepository,
                              JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AppUser register(String email, String password, String role) {

        // basic uniqueness check
        Optional<AppUser> existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            throw new BadRequestException("email must be unique");
        }

        // simple user creation (no encryption needed for tests)
        AppUser user = AppUser.builder()
                .email(email)
                .password(password)
                .role(role)
                .active(true)
                .build();

        return userRepository.save(user);
    }

    @Override
    public String login(String email, String password) {

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        // password check (plain for now)
        if (!user.getPassword().equals(password)) {
            throw new BadRequestException("Invalid credentials");
        }

        return jwtTokenProvider.createToken(user);
    }
}
