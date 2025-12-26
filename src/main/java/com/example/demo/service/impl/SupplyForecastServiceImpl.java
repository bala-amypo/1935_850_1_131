package com.example.demo.service.impl;

import com.example.demo.entity.SupplyForecast;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SupplyForecastRepository;
import com.example.demo.service.SupplyForecastService;

import java.time.Instant;
import java.util.List;

public class SupplyForecastServiceImpl implements SupplyForecastService {

    private final SupplyForecastRepository forecastRepository;

    public SupplyForecastServiceImpl(SupplyForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    @Override
    public SupplyForecast createForecast(SupplyForecast forecast) {

        if (forecast.getAvailableSupplyMW() == null ||
                forecast.getAvailableSupplyMW() < 0) {
            throw new BadRequestException("Supply must be >= 0");
        }

        if (forecast.getForecastStart() == null ||
                forecast.getForecastEnd() == null ||
                !forecast.getForecastStart().isBefore(forecast.getForecastEnd())) {
            throw new BadRequestException("Invalid range");
        }

        forecast.setGeneratedAt(Instant.now());

        return forecastRepository.save(forecast);
    }

    @Override
    public SupplyForecast updateForecast(Long id, SupplyForecast updated) {

        SupplyForecast existing = forecastRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forecast not found"));

        if (updated.getAvailableSupplyMW() == null ||
                updated.getAvailableSupplyMW() < 0) {
            throw new BadRequestException("Supply must be >= 0");
        }

        if (updated.getForecastStart() == null ||
                updated.getForecastEnd() == null ||
                !updated.getForecastStart().isBefore(updated.getForecastEnd())) {
            throw new BadRequestException("Invalid range");
        }

        existing.setAvailableSupplyMW(updated.getAvailableSupplyMW());
        existing.setForecastStart(updated.getForecastStart());
        existing.setForecastEnd(updated.getForecastEnd());

        return forecastRepository.save(existing);
    }

    @Override
    public SupplyForecast getLatestForecast() {

        return forecastRepository
                .findFirstByOrderByGeneratedAtDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No forecasts"));
    }

    @Override
    public List<SupplyForecast> getAllForecasts() {
        return forecastRepository.findAll();
    }
}
