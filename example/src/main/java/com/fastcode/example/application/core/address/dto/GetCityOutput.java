package com.fastcode.example.application.core.address.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCityOutput {

    private String city;
    private Integer cityId;
    private LocalDateTime lastUpdate;
    private Integer addressAddressId;
}
