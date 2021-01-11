package com.fastcode.example.application.extended.filmactor;

import com.fastcode.example.application.core.filmactor.FilmActorAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.extended.actor.IActorRepositoryExtended;
import com.fastcode.example.domain.extended.film.IFilmRepositoryExtended;
import com.fastcode.example.domain.extended.filmactor.IFilmActorRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("filmActorAppServiceExtended")
public class FilmActorAppServiceExtended extends FilmActorAppService implements IFilmActorAppServiceExtended {

    public FilmActorAppServiceExtended(
        IFilmActorRepositoryExtended filmActorRepositoryExtended,
        IActorRepositoryExtended actorRepositoryExtended,
        IFilmRepositoryExtended filmRepositoryExtended,
        IFilmActorMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(filmActorRepositoryExtended, actorRepositoryExtended, filmRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
