package com.example.demo.repository;

import com.example.demo.entity.Zone;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> {

    Optional<Zone> findByZoneName(String zoneName);

    List<Zone> findByActiveTrueOrderByPriorityLevelAsc();
}
