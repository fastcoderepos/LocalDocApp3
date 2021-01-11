package com.fastcode.example.application.extended.actor;

import com.fastcode.example.application.core.actor.IActorMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IActorMapperExtended extends IActorMapper {}
