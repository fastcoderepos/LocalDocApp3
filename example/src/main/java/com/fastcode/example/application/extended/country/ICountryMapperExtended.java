package com.fastcode.example.application.extended.country;

import com.fastcode.example.application.core.country.ICountryMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICountryMapperExtended extends ICountryMapper {}
