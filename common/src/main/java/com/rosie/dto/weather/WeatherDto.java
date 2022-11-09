package com.rosie.dto.weather;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class WeatherDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4796428520610109569L;

    private String weatherName;

    private String weatherType;

    private LocalDateTime localDateTime;

    private LocalDate localDate;

    private LocalTime localTime;
}
