package com.fastcode.example.application.core.country.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCountryOutput {

    private String country;
    private Integer countryId;
    private LocalDateTime lastUpdate;
}
