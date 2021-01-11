package com.fastcode.example.application.core.address.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindAddressByIdOutput {

    private String address;
    private String address2;
    private Integer addressId;
    private String district;
    private LocalDateTime lastUpdate;
    private String phone;
    private String postalCode;
    private Integer cityId;
    private Integer cityDescriptiveField;
    private Long versiono;
}
