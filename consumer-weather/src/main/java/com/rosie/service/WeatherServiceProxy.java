package com.rosie.service;

import com.rosie.dto.weather.WeatherDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("provide-weather")
public interface WeatherServiceProxy {

    @RequestMapping("weather-provider")
    WeatherDto weather();
}
