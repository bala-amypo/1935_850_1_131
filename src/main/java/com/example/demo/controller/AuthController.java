package com.example.demo.controller;

import com.example.demo.service.AppUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AppUserService userService;

    public AuthController(AppUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String role) {
        userService.register(email, password, role);
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {
        return userService.login(email, password);
    }
}
