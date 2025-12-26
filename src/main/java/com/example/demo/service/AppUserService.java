package com.example.demo.service;

import com.example.demo.entity.AppUser;

public interface AppUserService {

    AppUser createUser(AppUser user);

    AppUser getUserByEmail(String email);
}
