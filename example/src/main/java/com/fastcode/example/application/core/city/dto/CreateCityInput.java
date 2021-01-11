package com.fastcode.example.application.core.city.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CreateCityInput {

    @NotNull(message = "city Should not be null")
    @Length(max = 50, message = "city must be less than 50 characters")
    private String city;

    @NotNull(message = "cityId Should not be null")
    private Integer cityId;

    @NotNull(message = "lastUpdate Should not be null")
    private LocalDateTime lastUpdate;

    private Integer countryId;
}
