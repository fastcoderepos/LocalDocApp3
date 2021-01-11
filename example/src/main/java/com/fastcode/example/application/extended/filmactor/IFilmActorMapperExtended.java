package com.fastcode.example.application.extended.filmactor;

import com.fastcode.example.application.core.filmactor.IFilmActorMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFilmActorMapperExtended extends IFilmActorMapper {}
