package com.fastcode.example.application.extended.address;

import com.fastcode.example.application.core.address.IAddressMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAddressMapperExtended extends IAddressMapper {}
