package com.example.demo.controller;

import com.example.demo.entity.DemandReading;
import com.example.demo.service.DemandReadingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demand-readings")
public class DemandReadingController {

    private final DemandReadingService readingService;

    public DemandReadingController(DemandReadingService readingService) {
        this.readingService = readingService;
    }

    @PostMapping
    public DemandReading create(@RequestBody DemandReading reading) {
        return readingService.createReading(reading);
    }

    @GetMapping("/{id}")
    public DemandReading getById(@PathVariable Long id) {
        return readingService.getLatestReading(id);
    }

    @GetMapping("/zone/{zoneId}")
    public List<DemandReading> getForZone(@PathVariable Long zoneId) {
        return readingService.getReadingsForZone(zoneId);
    }

    @GetMapping("/zone/{zoneId}/latest")
    public DemandReading getLatest(@PathVariable Long zoneId) {
        return readingService.getLatestReading(zoneId);
    }

    @GetMapping("/zone/{zoneId}/recent")
    public List<DemandReading> getRecent(
            @PathVariable Long zoneId,
            @RequestParam int limit) {
        return readingService.getRecentReadings(zoneId, limit);
    }
}
