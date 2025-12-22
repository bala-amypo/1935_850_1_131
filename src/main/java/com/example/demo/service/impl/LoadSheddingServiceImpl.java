package com.example.demo.service.impl;

import com.example.demo.entity.LoadSheddingEvent;
import com.example.demo.repository.LoadSheddingEventRepository;
import com.example.demo.service.LoadSheddingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadSheddingServiceImpl implements LoadSheddingService {

    private final LoadSheddingEventRepository eventRepository;

    public LoadSheddingServiceImpl(
            LoadSheddingEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public LoadSheddingEvent createEvent(LoadSheddingEvent event) {
        return eventRepository.save(event);
    }

    @Override
    public LoadSheddingEvent updateEvent(Long id, LoadSheddingEvent event) {

        LoadSheddingEvent existing =
                eventRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setZone(event.getZone());
        existing.setEventStart(event.getEventStart());
        existing.setEventEnd(event.getEventEnd());
        existing.setReason(event.getReason());
        existing.setTriggeredByForecastId(event.getTriggeredByForecastId());
        existing.setExpectedDemandReductionMW(
                event.getExpectedDemandReductionMW());

        return eventRepository.save(existing);
    }

    @Override
    public LoadSheddingEvent getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public List<LoadSheddingEvent> getEventsForZone(Long zoneId) {
        return eventRepository.findByZoneIdOrderByEventStartDesc(zoneId);
    }

    @Override
    public List<LoadSheddingEvent> getAllEvents() {
        return eventRepository.findAll();
    }
}
