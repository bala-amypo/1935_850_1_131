package com.example.demo.controller;

import com.example.demo.entity.LoadSheddingEvent;
import com.example.demo.service.LoadSheddingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/load-shedding")
public class LoadSheddingController {

    private final LoadSheddingService loadSheddingService;

    public LoadSheddingController(
            LoadSheddingService loadSheddingService) {
        this.loadSheddingService = loadSheddingService;
    }

    @PostMapping
    public LoadSheddingEvent createEvent(
            @RequestBody LoadSheddingEvent event) {
        return loadSheddingService.createEvent(event);
    }

    @PutMapping("/{id}")
    public LoadSheddingEvent updateEvent(
            @PathVariable Long id,
            @RequestBody LoadSheddingEvent event) {
        return loadSheddingService.updateEvent(id, event);
    }

    @GetMapping("/{id}")
    public LoadSheddingEvent getEvent(
            @PathVariable Long id) {
        return loadSheddingService.getEventById(id);
    }

    @GetMapping("/zone/{zoneId}")
    public List<LoadSheddingEvent> getEventsForZone(
            @PathVariable Long zoneId) {
        return loadSheddingService.getEventsForZone(zoneId);
    }

    @GetMapping
    public List<LoadSheddingEvent> getAllEvents() {
        return loadSheddingService.getAllEvents();
    }
}
