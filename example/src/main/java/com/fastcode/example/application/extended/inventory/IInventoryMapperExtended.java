package com.fastcode.example.application.extended.inventory;

import com.fastcode.example.application.core.inventory.IInventoryMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInventoryMapperExtended extends IInventoryMapper {}
