package com.example.demo.controller;

import com.example.demo.entity.AppUser;
import com.example.demo.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/token")
    public String createToken(@RequestBody AuthRequest request) {
        AppUser user = AppUser.builder()
                .id(request.userId)
                .email(request.email)
                .role(request.role)
                .build();

        return jwtTokenProvider.createToken(user);
    }

    // ✅ NO external DTO file
    static class AuthRequest {
        public Long userId;
        public String email;
        public String role;
    }
}
