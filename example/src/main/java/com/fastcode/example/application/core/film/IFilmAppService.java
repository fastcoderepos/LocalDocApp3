package com.fastcode.example.application.core.film;

import com.fastcode.example.application.core.film.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IFilmAppService {
    //CRUD Operations

    CreateFilmOutput create(CreateFilmInput film);

    void delete(Integer id);

    UpdateFilmOutput update(Integer id, UpdateFilmInput input);

    FindFilmByIdOutput findById(Integer id);

    List<FindFilmByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations

    GetLanguageOutput getLanguage(Integer filmid);

    //Join Column Parsers

    Map<String, String> parseFilmActorsJoinColumn(String keysString);

    Map<String, String> parseFilmCategorysJoinColumn(String keysString);

    Map<String, String> parseInventorysJoinColumn(String keysString);
}
