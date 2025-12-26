package com.example.demo.repository;

import com.example.demo.entity.DemandReading;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandReadingRepository extends JpaRepository<DemandReading, Long> {

    Optional<DemandReading> findFirstByZoneIdOrderByRecordedAtDesc(Long zoneId);

    List<DemandReading> findByZoneIdOrderByRecordedAtDesc(Long zoneId);
}
