package com.example.demo.controller;

import com.example.demo.entity.Zone;
import com.example.demo.service.ZoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping
    public Zone create(@RequestBody Zone zone) {
        return zoneService.createZone(zone);
    }

    @PutMapping("/{id}")
    public Zone update(@PathVariable Long id, @RequestBody Zone zone) {
        return zoneService.updateZone(id, zone);
    }

    @GetMapping("/{id}")
    public Zone get(@PathVariable Long id) {
        return zoneService.getZoneById(id);
    }

    @GetMapping
    public List<Zone> all() {
        return zoneService.getAllZones();
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id) {
        zoneService.deactivateZone(id);
    }
}
