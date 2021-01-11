package com.fastcode.example.application.extended.film;

import com.fastcode.example.application.core.film.IFilmMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFilmMapperExtended extends IFilmMapper {}
