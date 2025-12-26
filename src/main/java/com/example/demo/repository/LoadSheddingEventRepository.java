package com.example.demo.repository;

import com.example.demo.entity.LoadSheddingEvent;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoadSheddingEventRepository extends JpaRepository<LoadSheddingEvent, Long> {

    List<LoadSheddingEvent> findByZoneIdOrderByEventStartDesc(Long zoneId);
}
