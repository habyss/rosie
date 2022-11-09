package com.rosie.controller;

import com.rosie.dto.weather.WeatherDto;
import com.rosie.response.BaseResponse;
import com.rosie.response.JsonResult;
import com.rosie.service.WeatherServiceProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("app")
public class WeatherController {

    @Resource
    WeatherServiceProxy weatherServiceProxy;

    @GetMapping("weather")
    public BaseResponse<WeatherDto> weather() {
        WeatherDto weather = weatherServiceProxy.weather();
        return JsonResult.successResult(weather);
    }
}
