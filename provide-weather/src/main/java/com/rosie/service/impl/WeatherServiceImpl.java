package com.rosie.service.impl;

import com.rosie.dto.weather.WeatherDto;
import com.rosie.service.WeatherService;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    public WeatherDto weather() {
        return new WeatherDto().setWeatherName("weatherName").setWeatherType("weatherType");
    }
}
