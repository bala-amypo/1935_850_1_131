package com.example.demo.controller;

import com.example.demo.entity.LoadSheddingEvent;
import com.example.demo.service.LoadSheddingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/load-shedding")
public class LoadSheddingController {

    private final LoadSheddingService sheddingService;

    public LoadSheddingController(LoadSheddingService sheddingService) {
        this.sheddingService = sheddingService;
    }

    @PostMapping("/trigger/{forecastId}")
    public LoadSheddingEvent trigger(@PathVariable Long forecastId) {
        return sheddingService.triggerLoadShedding(forecastId);
    }

    @GetMapping("/{id}")
    public LoadSheddingEvent getById(@PathVariable Long id) {
        return sheddingService.getEventById(id);
    }

    @GetMapping("/zone/{zoneId}")
    public List<LoadSheddingEvent> getForZone(@PathVariable Long zoneId) {
        return sheddingService.getEventsForZone(zoneId);
    }

    @GetMapping
    public List<LoadSheddingEvent> getAll() {
        return sheddingService.getAllEvents();
    }
}
