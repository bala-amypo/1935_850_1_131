package com.example.demo.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    private Long id;
    private String email;
    private String password;
    private String role;
    private Boolean active;
}
