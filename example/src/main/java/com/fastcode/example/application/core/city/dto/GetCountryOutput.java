package com.fastcode.example.application.core.city.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCountryOutput {

    private String country;
    private Integer countryId;
    private LocalDateTime lastUpdate;
    private Integer cityCityId;
}
