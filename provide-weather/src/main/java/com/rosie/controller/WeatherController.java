package com.rosie.controller;

import com.rosie.dto.weather.WeatherDto;
import com.rosie.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class WeatherController {

    @Resource
    WeatherService weatherService;

    @GetMapping("weather-provider")
    public WeatherDto weather() {
        return weatherService.weather();
    }
}