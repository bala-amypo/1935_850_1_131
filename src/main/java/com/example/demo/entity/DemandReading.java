package com.example.demo.entity;

import lombok.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandReading {

    private Long id;
    private Zone zone;
    private Double demandMW;
    private Instant recordedAt;
}
