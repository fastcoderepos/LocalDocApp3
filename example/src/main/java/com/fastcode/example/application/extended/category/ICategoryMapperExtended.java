package com.fastcode.example.application.extended.category;

import com.fastcode.example.application.core.category.ICategoryMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryMapperExtended extends ICategoryMapper {}
