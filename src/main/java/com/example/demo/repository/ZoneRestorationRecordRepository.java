package com.example.demo.repository;

import com.example.demo.entity.ZoneRestorationRecord;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRestorationRecordRepository extends JpaRepository<ZoneRestorationRecord, Long> {

    List<ZoneRestorationRecord> findByZoneIdOrderByRestoredAtDesc(Long zoneId);
}
