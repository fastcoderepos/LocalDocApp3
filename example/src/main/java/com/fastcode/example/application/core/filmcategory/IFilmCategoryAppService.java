package com.fastcode.example.application.core.filmcategory;

import com.fastcode.example.application.core.filmcategory.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.domain.core.filmcategory.FilmCategoryId;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IFilmCategoryAppService {
    //CRUD Operations

    CreateFilmCategoryOutput create(CreateFilmCategoryInput filmcategory);

    void delete(FilmCategoryId filmCategoryId);

    UpdateFilmCategoryOutput update(FilmCategoryId filmCategoryId, UpdateFilmCategoryInput input);

    FindFilmCategoryByIdOutput findById(FilmCategoryId filmCategoryId);

    List<FindFilmCategoryByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations
    //Relationship Operations

    GetCategoryOutput getCategory(FilmCategoryId filmCategoryId);

    GetFilmOutput getFilm(FilmCategoryId filmCategoryId);

    //Join Column Parsers

    FilmCategoryId parseFilmCategoryKey(String keysString);
}
