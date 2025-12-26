package com.example.demo.controller;

import com.example.demo.entity.DemandReading;
import com.example.demo.service.DemandReadingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/readings")
public class DemandReadingController {

    private final DemandReadingService readingService;

    public DemandReadingController(DemandReadingService readingService) {
        this.readingService = readingService;
    }

    @PostMapping
    public DemandReading create(@RequestBody DemandReading reading) {
        return readingService.createReading(reading);
    }

    @GetMapping("/zone/{zoneId}")
    public List<DemandReading> getByZone(@PathVariable Long zoneId) {
        return readingService.getReadingsForZone(zoneId);
    }

    @GetMapping("/zone/{zoneId}/latest")
    public DemandReading getLatest(@PathVariable Long zoneId) {
        return readingService.getLatestReading(zoneId);
    }

    @GetMapping("/zone/{zoneId}/recent")
    public List<DemandReading> getRecent(
            @PathVariable Long zoneId,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return readingService.getRecentReadings(zoneId, limit);
    }
}
