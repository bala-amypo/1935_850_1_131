package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneName;

    private Integer priorityLevel;

    private Integer population;

    @Builder.Default
    private Boolean active = true;

    private Instant createdAt;

    private Instant updatedAt;
}
