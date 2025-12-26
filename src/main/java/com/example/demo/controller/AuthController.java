package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.AppUser;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AppUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AppUserService userService;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AppUserService userService, JwtTokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        AppUser user = userService.authenticate(request.getEmail(), request.getPassword());
        return tokenProvider.createToken(user);
    }
}
