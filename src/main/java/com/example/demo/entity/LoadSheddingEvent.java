package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class LoadSheddingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    private Instant eventStart;

    private Instant eventEnd;

    private String reason;

    private Long triggeredByForecastId;

    private Double expectedDemandReductionMW;

    public LoadSheddingEvent() {}

    /* ---------- GETTERS ---------- */

    public Long getId() {
        return id;
    }

    public Zone getZone() {
        return zone;
    }

    public Instant getEventStart() {
        return eventStart;
    }

    public Instant getEventEnd() {
        return eventEnd;
    }

    public String getReason() {
        return reason;
    }

    public Long getTriggeredByForecastId() {
        return triggeredByForecastId;
    }

    public Double getExpectedDemandReductionMW() {
        return expectedDemandReductionMW;
    }

    /* ---------- SETTERS ---------- */

    public void setId(Long id) {
        this.id = id;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setEventStart(Instant eventStart) {
        this.eventStart = eventStart;
    }

    public void setEventEnd(Instant eventEnd) {
        this.eventEnd = eventEnd;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setTriggeredByForecastId(Long triggeredByForecastId) {
        this.triggeredByForecastId = triggeredByForecastId;
    }

    public void setExpectedDemandReductionMW(Double expectedDemandReductionMW) {
        this.expectedDemandReductionMW = expectedDemandReductionMW;
    }
}
