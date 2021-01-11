package com.fastcode.example.application.core.filmcategory;

import com.fastcode.example.application.core.filmcategory.dto.*;
import com.fastcode.example.domain.core.category.CategoryEntity;
import com.fastcode.example.domain.core.film.FilmEntity;
import com.fastcode.example.domain.core.filmcategory.FilmCategoryEntity;
import java.time.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IFilmCategoryMapper {
    FilmCategoryEntity createFilmCategoryInputToFilmCategoryEntity(CreateFilmCategoryInput filmcategoryDto);

    @Mappings(
        {
            @Mapping(source = "entity.category.categoryId", target = "categoryDescriptiveField"),
            @Mapping(source = "entity.film.filmId", target = "filmDescriptiveField"),
        }
    )
    CreateFilmCategoryOutput filmCategoryEntityToCreateFilmCategoryOutput(FilmCategoryEntity entity);

    FilmCategoryEntity updateFilmCategoryInputToFilmCategoryEntity(UpdateFilmCategoryInput filmCategoryDto);

    @Mappings(
        {
            @Mapping(source = "entity.category.categoryId", target = "categoryDescriptiveField"),
            @Mapping(source = "entity.film.filmId", target = "filmDescriptiveField"),
        }
    )
    UpdateFilmCategoryOutput filmCategoryEntityToUpdateFilmCategoryOutput(FilmCategoryEntity entity);

    @Mappings(
        {
            @Mapping(source = "entity.category.categoryId", target = "categoryDescriptiveField"),
            @Mapping(source = "entity.film.filmId", target = "filmDescriptiveField"),
        }
    )
    FindFilmCategoryByIdOutput filmCategoryEntityToFindFilmCategoryByIdOutput(FilmCategoryEntity entity);

    @Mappings(
        {
            @Mapping(source = "category.categoryId", target = "categoryId"),
            @Mapping(source = "category.lastUpdate", target = "lastUpdate"),
            @Mapping(source = "foundFilmCategory.categoryId", target = "filmCategoryCategoryId"),
            @Mapping(source = "foundFilmCategory.filmId", target = "filmCategoryFilmId"),
        }
    )
    GetCategoryOutput categoryEntityToGetCategoryOutput(CategoryEntity category, FilmCategoryEntity foundFilmCategory);

    @Mappings(
        {
            @Mapping(source = "film.filmId", target = "filmId"),
            @Mapping(source = "film.lastUpdate", target = "lastUpdate"),
            @Mapping(source = "foundFilmCategory.categoryId", target = "filmCategoryCategoryId"),
            @Mapping(source = "foundFilmCategory.filmId", target = "filmCategoryFilmId"),
        }
    )
    GetFilmOutput filmEntityToGetFilmOutput(FilmEntity film, FilmCategoryEntity foundFilmCategory);
}
