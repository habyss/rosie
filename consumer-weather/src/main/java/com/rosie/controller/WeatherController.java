package com.rosie.controller;

import com.rosie.service.WeatherServiceProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("app")
public class WeatherController {

    @Resource
    WeatherServiceProxy weatherServiceProxy;

    @GetMapping("weather")
    public Object weather() {
        return weatherServiceProxy.weather();
    }
}
