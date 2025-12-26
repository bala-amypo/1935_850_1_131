package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "zone_restoration_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneRestorationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 REQUIRED BY TESTS
    @Column(nullable = false)
    private Long eventId;

    @ManyToOne(optional = false)
    private Zone zone;

    @Column(nullable = false)
    private Instant restoredAt;
}
