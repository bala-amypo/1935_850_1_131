package com.example.demo.service.impl;

import com.example.demo.entity.LoadSheddingEvent;
import com.example.demo.entity.Zone;
import com.example.demo.entity.ZoneRestorationRecord;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.LoadSheddingEventRepository;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.repository.ZoneRestorationRecordRepository;
import com.example.demo.service.ZoneRestorationService;

import java.time.Instant;
import java.util.List;

public class ZoneRestorationServiceImpl implements ZoneRestorationService {

    private final ZoneRestorationRecordRepository restorationRepo;
    private final LoadSheddingEventRepository eventRepo;
    private final ZoneRepository zoneRepo;

    // ⚠️ Constructor order MUST match tests
    public ZoneRestorationServiceImpl(ZoneRestorationRecordRepository restorationRepo,
                                      LoadSheddingEventRepository eventRepo,
                                      ZoneRepository zoneRepo) {
        this.restorationRepo = restorationRepo;
        this.eventRepo = eventRepo;
        this.zoneRepo = zoneRepo;
    }

    @Override
    public ZoneRestorationRecord restoreZone(ZoneRestorationRecord record) {

        // 1️⃣ Event must exist
        LoadSheddingEvent event = eventRepo.findById(record.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));

        // 2️⃣ Zone must exist
        Long zoneId = record.getZone() != null ? record.getZone().getId() : null;
        Zone zone = zoneRepo.findById(zoneId)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        // 3️⃣ restoredAt must be AFTER eventStart
        Instant restoredAt = record.getRestoredAt();
        if (restoredAt == null || !restoredAt.isAfter(event.getEventStart())) {
            throw new BadRequestException("restored time must be after event start");
        }

        record.setZone(zone);

        return restorationRepo.save(record);
    }

    @Override
    public ZoneRestorationRecord getRecordById(Long id) {

        return restorationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));
    }

    @Override
    public List<ZoneRestorationRecord> getRecordsForZone(Long zoneId) {

        return restorationRepo.findByZoneIdOrderByRestoredAtDesc(zoneId);
    }
}
