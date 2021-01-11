package com.fastcode.example.application.extended.language;

import com.fastcode.example.application.core.language.ILanguageMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ILanguageMapperExtended extends ILanguageMapper {}
