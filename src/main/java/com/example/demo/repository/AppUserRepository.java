package com.example.demo.repository;

import com.example.demo.entity.AppUser;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
}
