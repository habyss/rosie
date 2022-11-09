package com.rosie.service.impl;

import com.rosie.dto.weather.WeatherDto;
import com.rosie.service.WeatherService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class WeatherServiceImpl implements WeatherService {

    public WeatherDto weather() {
        return new WeatherDto()
                .setWeatherName("weatherName")
                .setWeatherType("weatherType")
                .setLocalDateTime(LocalDateTime.now())
                .setLocalDate(LocalDate.now())
                .setLocalTime(LocalTime.now());
    }
}
