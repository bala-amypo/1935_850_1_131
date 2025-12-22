package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class ZoneRestorationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    private Instant restoredAt;

    private Long eventId;

    private String notes;

    public ZoneRestorationRecord() {}

    /* ---------- GETTERS ---------- */

    public Long getId() {
        return id;
    }

    public Zone getZone() {
        return zone;
    }

    public Instant getRestoredAt() {
        return restoredAt;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getNotes() {
        return notes;
    }

    /* ---------- SETTERS ---------- */

    public void setId(Long id) {
        this.id = id;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setRestoredAt(Instant restoredAt) {
        this.restoredAt = restoredAt;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
