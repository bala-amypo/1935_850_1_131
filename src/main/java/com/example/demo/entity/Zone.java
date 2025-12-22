package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String zoneName;

    @Column(nullable = false)
    private Integer priorityLevel;

    private Integer population;

    private Boolean active = true;

    private Instant createdAt;
    private Instant updatedAt;

    public Zone() {}

    /* ---------- GETTERS ---------- */

    public Long getId() {
        return id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public Integer getPopulation() {
        return population;
    }

    public Boolean getActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /* ---------- SETTERS ---------- */

    public void setId(Long id) {
        this.id = id;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    /* ---------- JPA CALLBACKS ---------- */

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        if (this.active == null) {
            this.active = true;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
