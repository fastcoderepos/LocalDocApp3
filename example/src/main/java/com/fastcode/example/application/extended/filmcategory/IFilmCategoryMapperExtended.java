package com.fastcode.example.application.extended.filmcategory;

import com.fastcode.example.application.core.filmcategory.IFilmCategoryMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFilmCategoryMapperExtended extends IFilmCategoryMapper {}
