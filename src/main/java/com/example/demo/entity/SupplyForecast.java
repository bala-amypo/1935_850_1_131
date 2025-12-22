package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class SupplyForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double availableSupplyMW;

    private Instant forecastStart;

    private Instant forecastEnd;

    private Instant generatedAt;

    public SupplyForecast() {}

    /* ---------- GETTERS ---------- */

    public Long getId() {
        return id;
    }

    public Double getAvailableSupplyMW() {
        return availableSupplyMW;
    }

    public Instant getForecastStart() {
        return forecastStart;
    }

    public Instant getForecastEnd() {
        return forecastEnd;
    }

    public Instant getGeneratedAt() {
        return generatedAt;
    }

    /* ---------- SETTERS ---------- */

    public void setId(Long id) {
        this.id = id;
    }

    public void setAvailableSupplyMW(Double availableSupplyMW) {
        this.availableSupplyMW = availableSupplyMW;
    }

    public void setForecastStart(Instant forecastStart) {
        this.forecastStart = forecastStart;
    }

    public void setForecastEnd(Instant forecastEnd) {
        this.forecastEnd = forecastEnd;
    }

    public void setGeneratedAt(Instant generatedAt) {
        this.generatedAt = generatedAt;
    }

    /* ---------- JPA CALLBACK ---------- */

    @PrePersist
    public void onCreate() {
        this.generatedAt = Instant.now();
    }
}
