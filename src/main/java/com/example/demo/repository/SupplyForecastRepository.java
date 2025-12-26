package com.example.demo.repository;

import com.example.demo.entity.SupplyForecast;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyForecastRepository extends JpaRepository<SupplyForecast, Long> {

    Optional<SupplyForecast> findFirstByOrderByGeneratedAtDesc();
}
