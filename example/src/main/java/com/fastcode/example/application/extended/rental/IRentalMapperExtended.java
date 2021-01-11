package com.fastcode.example.application.extended.rental;

import com.fastcode.example.application.core.rental.IRentalMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRentalMapperExtended extends IRentalMapper {}
