package com.fastcode.example.application.core.film;

import com.fastcode.example.application.core.film.dto.*;
import com.fastcode.example.domain.core.film.FilmEntity;
import com.fastcode.example.domain.core.language.LanguageEntity;
import java.time.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IFilmMapper {
    FilmEntity createFilmInputToFilmEntity(CreateFilmInput filmDto);

    @Mappings(
        {
            @Mapping(source = "entity.language.languageId", target = "languageId"),
            @Mapping(source = "entity.language.languageId", target = "languageDescriptiveField"),
        }
    )
    CreateFilmOutput filmEntityToCreateFilmOutput(FilmEntity entity);

    FilmEntity updateFilmInputToFilmEntity(UpdateFilmInput filmDto);

    @Mappings(
        {
            @Mapping(source = "entity.language.languageId", target = "languageId"),
            @Mapping(source = "entity.language.languageId", target = "languageDescriptiveField"),
        }
    )
    UpdateFilmOutput filmEntityToUpdateFilmOutput(FilmEntity entity);

    @Mappings(
        {
            @Mapping(source = "entity.language.languageId", target = "languageId"),
            @Mapping(source = "entity.language.languageId", target = "languageDescriptiveField"),
        }
    )
    FindFilmByIdOutput filmEntityToFindFilmByIdOutput(FilmEntity entity);

    @Mappings(
        {
            @Mapping(source = "language.lastUpdate", target = "lastUpdate"),
            @Mapping(source = "foundFilm.filmId", target = "filmFilmId"),
        }
    )
    GetLanguageOutput languageEntityToGetLanguageOutput(LanguageEntity language, FilmEntity foundFilm);
}
