package com.example.demo.service.impl;

import com.example.demo.entity.AppUser;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.AppUserService;

public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepo;

    public AppUserServiceImpl(AppUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public AppUser createUser(AppUser user) {
        return userRepo.save(user);
    }

    @Override
    public AppUser getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public AppUser getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
