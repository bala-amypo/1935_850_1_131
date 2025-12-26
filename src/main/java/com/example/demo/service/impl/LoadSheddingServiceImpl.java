package com.example.demo.service.impl;

import com.example.demo.entity.DemandReading;
import com.example.demo.entity.LoadSheddingEvent;
import com.example.demo.entity.SupplyForecast;
import com.example.demo.entity.Zone;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DemandReadingRepository;
import com.example.demo.repository.LoadSheddingEventRepository;
import com.example.demo.repository.SupplyForecastRepository;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.LoadSheddingService;

import java.time.Instant;
import java.util.List;

public class LoadSheddingServiceImpl implements LoadSheddingService {

    private final SupplyForecastRepository forecastRepo;
    private final ZoneRepository zoneRepo;
    private final DemandReadingRepository readingRepo;
    private final LoadSheddingEventRepository eventRepo;

    // ⚠️ Constructor order MUST match tests
    public LoadSheddingServiceImpl(SupplyForecastRepository forecastRepo,
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

        // 1️⃣ Forecast must exist
        SupplyForecast forecast = forecastRepo.findById(forecastId)
                .orElseThrow(() -> new ResourceNotFoundException("Forecast not found"));

        // 2️⃣ Fetch active zones ordered by priority (ascending)
        List<Zone> zones = zoneRepo.findByActiveTrueOrderByPriorityLevelAsc();

        if (zones.isEmpty()) {
            throw new BadRequestException("No overload");
        }

        // 3️⃣ Calculate total demand & find shedding candidate
        double totalDemand = 0.0;
        Zone selectedZone = null;
        DemandReading selectedReading = null;

        for (Zone zone : zones) {

            var latestReadingOpt =
                    readingRepo.findFirstByZoneIdOrderByRecordedAtDesc(zone.getId());

            if (latestReadingOpt.isEmpty()) {
                continue;
            }

            DemandReading reading = latestReadingOpt.get();
            totalDemand += reading.getDemandMW();

            // select highest priority number (least important zone)
            if (selectedZone == null ||
                    zone.getPriorityLevel() > selectedZone.getPriorityLevel()) {
                selectedZone = zone;
                selectedReading = reading;
            }
        }

        // 4️⃣ Check overload
        if (totalDemand <= forecast.getAvailableSupplyMW()) {
            throw new BadRequestException("No overload");
        }

        if (selectedZone == null || selectedReading == null) {
            throw new BadRequestException("No suitable zone");
        }

        // 5️⃣ Create shedding event
        LoadSheddingEvent event = LoadSheddingEvent.builder()
                .zone(selectedZone)
                .eventStart(Instant.now())
                .reason("Automatic load shedding")
                .triggeredByForecastId(forecastId)
                .expectedDemandReductionMW(selectedReading.getDemandMW())
                .build();

        // ⚠️ Do NOT catch exceptions here — tests expect propagation
        return eventRepo.save(event);
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
