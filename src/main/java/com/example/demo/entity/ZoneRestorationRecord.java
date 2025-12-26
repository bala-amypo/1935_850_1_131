package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "zone_restorations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneRestorationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Zone zone;

    @ManyToOne(optional = false)
    private LoadSheddingEvent event;

    @Column(nullable = false)
    private Instant restoredAt;

    // Convenience method for service usage
    public Long getEventId() {
        return event != null ? event.getId() : null;
    }
}
