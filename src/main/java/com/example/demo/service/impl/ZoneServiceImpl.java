package com.example.demo.service.impl;

import com.example.demo.entity.Zone;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ZoneRepository;
import com.example.demo.service.ZoneService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;

    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Zone createZone(Zone zone) {

        // name must be unique
        if (zone.getZoneName() != null &&
                zoneRepository.findByZoneName(zone.getZoneName()).isPresent()) {
            throw new BadRequestException("Zone name must be unique");
        }

        // priority validation
        if (zone.getPriorityLevel() == null || zone.getPriorityLevel() < 1) {
            throw new BadRequestException("Priority must be >= 1");
        }

        // defaults
        if (zone.getActive() == null) {
            zone.setActive(true);
        }

        zone.setCreatedAt(Instant.now());
        zone.setUpdatedAt(Instant.now());

        return zoneRepository.save(zone);
    }

    @Override
    public Zone updateZone(Long id, Zone zone) {

        Zone existing = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        if (zone.getZoneName() != null) {
            existing.setZoneName(zone.getZoneName());
        }

        if (zone.getPriorityLevel() != null) {
            if (zone.getPriorityLevel() < 1) {
                throw new BadRequestException("Priority must be >= 1");
            }
            existing.setPriorityLevel(zone.getPriorityLevel());
        }

        if (zone.getPopulation() != null) {
            existing.setPopulation(zone.getPopulation());
        }

        if (zone.getActive() != null) {
            existing.setActive(zone.getActive());
        }

        existing.setUpdatedAt(Instant.now());

        return zoneRepository.save(existing);
    }

    @Override
    public Zone getZoneById(Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));
    }

    @Override
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    @Override
    public void deactivateZone(Long id) {

        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found"));

        zone.setActive(false);
        zone.setUpdatedAt(Instant.now());

        zoneRepository.save(zone);
    }
}
