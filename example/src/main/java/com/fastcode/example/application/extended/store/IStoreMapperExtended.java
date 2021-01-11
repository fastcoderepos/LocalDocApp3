package com.fastcode.example.application.extended.store;

import com.fastcode.example.application.core.store.IStoreMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IStoreMapperExtended extends IStoreMapper {}
