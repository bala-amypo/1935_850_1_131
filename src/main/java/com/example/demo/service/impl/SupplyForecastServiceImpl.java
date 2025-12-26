package com.example.demo.service.impl;

import com.example.demo.entity.SupplyForecast;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SupplyForecastRepository;
import com.example.demo.service.SupplyForecastService;

import java.time.Instant;
import java.util.List;

public class SupplyForecastServiceImpl implements SupplyForecastService {

    private final SupplyForecastRepository forecastRepo;

    // ⚠️ Constructor order MUST match tests
    public SupplyForecastServiceImpl(SupplyForecastRepository forecastRepo) {
        this.forecastRepo = forecastRepo;
    }

    @Override
    public SupplyForecast createForecast(SupplyForecast forecast) {

        // supply >= 0
        if (forecast.getAvailableSupplyMW() == null ||
                forecast.getAvailableSupplyMW() < 0) {
            throw new BadRequestException("supply must be >= 0");
        }

        // valid time range
        if (forecast.getForecastStart() == null ||
                forecast.getForecastEnd() == null ||
                !forecast.getForecastStart().isBefore(forecast.getForecastEnd())) {
            throw new BadRequestException("invalid range");
        }

        // generatedAt must be set
        forecast.setGeneratedAt(Instant.now());

        return forecastRepo.save(forecast);
    }

    @Override
    public SupplyForecast updateForecast(Long id, SupplyForecast forecast) {

        SupplyForecast existing = forecastRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forecast not found"));

        // supply >= 0
        if (forecast.getAvailableSupplyMW() == null ||
                forecast.getAvailableSupplyMW() < 0) {
            throw new BadRequestException("supply must be >= 0");
        }

        // valid time range
        if (forecast.getForecastStart() == null ||
                forecast.getForecastEnd() == null ||
                !forecast.getForecastStart().isBefore(forecast.getForecastEnd())) {
            throw new BadRequestException("invalid range");
        }

        existing.setAvailableSupplyMW(forecast.getAvailableSupplyMW());
        existing.setForecastStart(forecast.getForecastStart());
        existing.setForecastEnd(forecast.getForecastEnd());

        return forecastRepo.save(existing);
    }

    @Override
    public SupplyForecast getForecastById(Long id) {
        return forecastRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forecast not found"));
    }

    @Override
    public SupplyForecast getLatestForecast() {

        return forecastRepo.findFirstByOrderByGeneratedAtDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No forecasts"));
    }

    @Override
    public List<SupplyForecast> getAllForecasts() {
        return forecastRepo.findAll();
    }
}
