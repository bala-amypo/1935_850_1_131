package com.example.demo.service.impl;

import com.example.demo.entity.SupplyForecast;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SupplyForecastRepository;
import com.example.demo.service.SupplyForecastService;

import java.util.List;

public class SupplyForecastServiceImpl implements SupplyForecastService {

    private final SupplyForecastRepository forecastRepository;

    public SupplyForecastServiceImpl(SupplyForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    @Override
    public SupplyForecast createForecast(SupplyForecast forecast) {

        if (forecast.getAvailableSupplyMW() < 0) {
            throw new BadRequestException("availableSupplyMW must be >= 0");
        }

        if (!forecast.getForecastStart().isBefore(forecast.getForecastEnd())) {
            throw new BadRequestException("Invalid range");
        }

        forecast.setId(null);
        return forecastRepository.save(forecast);
    }

    @Override
    public SupplyForecast updateForecast(Long id, SupplyForecast forecast) {

        SupplyForecast existing = forecastRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forecast not found"));

        if (forecast.getAvailableSupplyMW() < 0) {
            throw new BadRequestException("availableSupplyMW must be >= 0");
        }

        if (!forecast.getForecastStart().isBefore(forecast.getForecastEnd())) {
            throw new BadRequestException("Invalid range");
        }

        existing.setAvailableSupplyMW(forecast.getAvailableSupplyMW());
        existing.setForecastStart(forecast.getForecastStart());
        existing.setForecastEnd(forecast.getForecastEnd());

        return forecastRepository.save(existing);
    }

    @Override
    public SupplyForecast getForecastById(Long id) {
        return forecastRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forecast not found"));
    }

    @Override
    public SupplyForecast getLatestForecast() {
        return forecastRepository.findFirstByOrderByGeneratedAtDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No forecasts"));
    }

    @Override
    public List<SupplyForecast> getAllForecasts() {
        return forecastRepository.findAll();
    }
}
