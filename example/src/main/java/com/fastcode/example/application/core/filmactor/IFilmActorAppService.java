package com.fastcode.example.application.core.filmactor;

import com.fastcode.example.application.core.filmactor.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.domain.core.filmactor.FilmActorId;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IFilmActorAppService {
    //CRUD Operations

    CreateFilmActorOutput create(CreateFilmActorInput filmactor);

    void delete(FilmActorId filmActorId);

    UpdateFilmActorOutput update(FilmActorId filmActorId, UpdateFilmActorInput input);

    FindFilmActorByIdOutput findById(FilmActorId filmActorId);

    List<FindFilmActorByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations
    //Relationship Operations

    GetActorOutput getActor(FilmActorId filmActorId);

    GetFilmOutput getFilm(FilmActorId filmActorId);

    //Join Column Parsers

    FilmActorId parseFilmActorKey(String keysString);
}
