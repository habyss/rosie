package com.rosie.dto.weather;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class WeatherDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -4796428520610109569L;

    private String weatherName;

    private String weatherType;
}
