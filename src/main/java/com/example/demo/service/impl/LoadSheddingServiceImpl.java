package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.LoadSheddingService;

import java.time.Instant;
import java.util.List;

public class LoadSheddingServiceImpl implements LoadSheddingService {

    private final SupplyForecastRepository forecastRepo;
    private final ZoneRepository zoneRepo;
    private final DemandReadingRepository readingRepo;
    private final LoadSheddingEventRepository eventRepo;

    // Constructor order MUST match tests
    public LoadSheddingServiceImpl(
            SupplyForecastRepository forecastRepo,
            ZoneRepository zoneRepo,
            DemandReadingRepository readingRepo,
            LoadSheddingEventRepository eventRepo) {
        this.forecastRepo = forecastRepo;
        this.zoneRepo = zoneRepo;
        this.readingRepo = readingRepo;
        this.eventRepo = eventRepo;
    }

    @Override
    public LoadSheddingEvent triggerLoadShedding(Long forecastId) {

        SupplyForecast forecast = forecastRepo.findById(forecastId)
                .orElseThrow(() -> new ResourceNotFoundException("Forecast not found"));

        List<Zone> activeZones = zoneRepo.findByActiveTrueOrderByPriorityLevelAsc();

        if (activeZones.isEmpty()) {
            throw new BadRequestException("No suitable zones");
        }

        double totalDemand = 0.0;

        for (Zone zone : activeZones) {
            readingRepo.findFirstByZoneIdOrderByRecordedAtDesc(zone.getId())
                    .ifPresent(r -> totalDemand += r.getDemandMW());
        }

        if (totalDemand <= forecast.getAvailableSupplyMW()) {
            throw new BadRequestException("No overload");
        }

        for (Zone zone : activeZones) {

            DemandReading latest = readingRepo
                    .findFirstByZoneIdOrderByRecordedAtDesc(zone.getId())
                    .orElse(null);

            if (latest == null) continue;

            double reduction = latest.getDemandMW();

            LoadSheddingEvent event = LoadSheddingEvent.builder()
                    .zone(zone)
                    .eventStart(Instant.now())
                    .reason("Automatic load shedding")
                    .triggeredByForecastId(forecastId)
                    .expectedDemandReductionMW(Math.max(0, reduction))
                    .build();

            return eventRepo.save(event);
        }

        throw new BadRequestException("No suitable zones");
    }

    @Override
    public LoadSheddingEvent getEventById(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }

    @Override
    public List<LoadSheddingEvent> getEventsForZone(Long zoneId) {
        return eventRepo.findByZoneIdOrderByEventStartDesc(zoneId);
    }

    @Override
    public List<LoadSheddingEvent> getAllEvents() {
        return eventRepo.findAll();
    }
}
