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
    public LoadSheddingEvent get(@PathVariable Long id) {
        return sheddingService.getEventById(id);
    }

    @GetMapping
    public List<LoadSheddingEvent> all() {
        return sheddingService.getAllEvents();
    }

    @GetMapping("/zone/{zoneId}")
    public List<LoadSheddingEvent> byZone(@PathVariable Long zoneId) {
        return sheddingService.getEventsForZone(zoneId);
    }
}
