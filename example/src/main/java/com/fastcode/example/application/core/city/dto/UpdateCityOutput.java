package com.fastcode.example.application.core.city.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCityOutput {

    private String city;
    private Integer cityId;
    private LocalDateTime lastUpdate;
    private Integer countryId;
    private Integer countryDescriptiveField;
}
