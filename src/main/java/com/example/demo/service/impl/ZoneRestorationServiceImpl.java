package com.example.demo.service.impl;

import com.example.demo.entity.ZoneRestorationRecord;
import com.example.demo.repository.ZoneRestorationRecordRepository;
import com.example.demo.service.ZoneRestorationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneRestorationServiceImpl implements ZoneRestorationService {

    private final ZoneRestorationRecordRepository restorationRepository;

    public ZoneRestorationServiceImpl(
            ZoneRestorationRecordRepository restorationRepository) {
        this.restorationRepository = restorationRepository;
    }

    @Override
    public ZoneRestorationRecord restoreZone(ZoneRestorationRecord record) {
        return restorationRepository.save(record);
    }

    @Override
    public ZoneRestorationRecord getRecordById(Long id) {
        return restorationRepository.findById(id).orElse(null);
    }

    @Override
    public List<ZoneRestorationRecord> getRecordsForZone(Long zoneId) {
        return restorationRepository.findByZoneIdOrderByRestoredAtDesc(zoneId);
    }
}
