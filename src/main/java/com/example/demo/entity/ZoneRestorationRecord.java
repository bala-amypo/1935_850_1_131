package com.example.demo.entity;

import lombok.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneRestorationRecord {

    private Long id;
    private Zone zone;
    private Instant restoredAt;
    private Long eventId;
    private String notes;
}
