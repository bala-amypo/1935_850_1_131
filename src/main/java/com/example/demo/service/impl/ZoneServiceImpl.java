package com.example.demo.service.impl;

import com.example.demo.entity.Zone;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.ZoneService;

import java.time.Instant;
import java.util.List;

@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepo;

    // ⚠️ Constructor order MUST match tests
    public ZoneServiceImpl(ZoneRepository zoneRepo) {
        this.zoneRepo = zoneRepo;
    }

    @Override
    public Zone createZone(Zone zone) {

        // priority >= 1
        if (zone.getPriorityLevel() == null || zone.getPriorityLevel() < 1) {
            throw new BadRequestException("priority must be >= 1");
        }

        // unique name
        if (zoneRepo.findByZoneName(zone.getZoneName()).isPresent()) {
            throw new BadRequestException("zone name must be unique");
        }

        // defaults
        if (zone.getActive() == null) {
            zone.setActive(true);
        }

        Instant now = Instant.now();
        zone.setCreatedAt(now);
        zone.setUpdatedAt(now);

        return zoneRepo.save(zone);
    }

    @Override
    public Zone updateZone(Long id, Zone zone) {

        Zone existing = zoneRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        existing.setZoneName(zone.getZoneName());
        existing.setPriorityLevel(zone.getPriorityLevel());
        existing.setPopulation(zone.getPopulation());
        existing.setUpdatedAt(Instant.now());

        return zoneRepo.save(existing);
    }

    @Override
    public Zone getZoneById(Long id) {
        return zoneRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
    }

    @Override
    public List<Zone> getAllZones() {
        return zoneRepo.findAll();
    }

    @Override
    public void deactivateZone(Long id) {

        Zone zone = zoneRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        zone.setActive(false);
        zoneRepo.save(zone);
    }
}
