package com.example.demo.service.impl;

import com.example.demo.entity.SupplyForecast;
import com.example.demo.repository.SupplyForecastRepository;
import com.example.demo.service.SupplyForecastService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyForecastServiceImpl implements SupplyForecastService {

    private final SupplyForecastRepository forecastRepository;

    public SupplyForecastServiceImpl(
            SupplyForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    @Override
    public SupplyForecast createForecast(SupplyForecast forecast) {
        return forecastRepository.save(forecast);
    }

    @Override
    public SupplyForecast updateForecast(Long id, SupplyForecast forecast) {

        SupplyForecast existing =
                forecastRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setAvailableSupplyMW(forecast.getAvailableSupplyMW());
        existing.setForecastStart(forecast.getForecastStart());
        existing.setForecastEnd(forecast.getForecastEnd());

        return forecastRepository.save(existing);
    }

    @Override
    public SupplyForecast getForecastById(Long id) {
        return forecastRepository.findById(id).orElse(null);
    }

    @Override
    public SupplyForecast getLatestForecast() {
        return forecastRepository
                .findFirstByOrderByGeneratedAtDesc()
                .orElse(null);
    }

    @Override
    public List<SupplyForecast> getAllForecasts() {
        return forecastRepository.findAll();
    }
}
