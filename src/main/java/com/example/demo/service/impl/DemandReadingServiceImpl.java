package com.example.demo.service.impl;

import com.example.demo.entity.DemandReading;
import com.example.demo.entity.Zone;
import com.example.demo.exception.BadRequestException;
import org.springframework.stereotype.Service;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DemandReadingRepository;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.DemandReadingService;

import java.time.Instant;
import java.util.List;

@Service
public class DemandReadingServiceImpl implements DemandReadingService {

    private final DemandReadingRepository readingRepo;
    private final ZoneRepository zoneRepo;

    // ⚠️ Constructor order MUST match tests
    public DemandReadingServiceImpl(DemandReadingRepository readingRepo,
                                    ZoneRepository zoneRepo) {
        this.readingRepo = readingRepo;
        this.zoneRepo = zoneRepo;
    }

    @Override
    public DemandReading createReading(DemandReading reading) {

        Long zoneId = reading.getZone() != null ? reading.getZone().getId() : null;

        // zone must exist
        Zone zone = zoneRepo.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        // demand >= 0
        if (reading.getDemandMW() == null || reading.getDemandMW() < 0) {
            throw new BadRequestException("demand must be >= 0");
        }

        // recordedAt must not be in future
        if (reading.getRecordedAt() != null &&
                reading.getRecordedAt().isAfter(Instant.now())) {
            throw new BadRequestException("timestamp cannot be in future");
        }

        reading.setZone(zone);
        return readingRepo.save(reading);
    }

    @Override
    public List<DemandReading> getReadingsForZone(Long zoneId) {

        // ensure zone exists
        zoneRepo.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        return readingRepo.findByZoneIdOrderByRecordedAtDesc(zoneId);
    }

    @Override
    public DemandReading getLatestReading(Long zoneId) {

        return readingRepo.findFirstByZoneIdOrderByRecordedAtDesc(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("No readings"));
    }

    @Override
    public List<DemandReading> getRecentReadings(Long zoneId, int limit) {

        // ensure zone exists
        zoneRepo.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        List<DemandReading> all =
                readingRepo.findByZoneIdOrderByRecordedAtDesc(zoneId);

        if (all.size() <= limit) {
            return all;
        }
        return all.subList(0, limit);
    }
}
