package com.example.demo.controller;

import com.example.demo.entity.ZoneRestorationRecord;
import com.example.demo.service.ZoneRestorationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restorations")
public class ZoneRestorationController {

    private final ZoneRestorationService zoneRestorationService;

    public ZoneRestorationController(ZoneRestorationService zoneRestorationService) {
        this.zoneRestorationService = zoneRestorationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ZoneRestorationRecord restoreZone(
            @RequestBody ZoneRestorationRecord record) {
        return zoneRestorationService.restoreZone(record);
    }

    @GetMapping("/{id}")
    public ZoneRestorationRecord getRecordById(@PathVariable Long id) {
        return zoneRestorationService.getRecordById(id);
    }

    @GetMapping("/zone/{zoneId}")
    public List<ZoneRestorationRecord> getRecordsForZone(
            @PathVariable Long zoneId) {
        return zoneRestorationService.getRecordsForZone(zoneId);
    }
}
