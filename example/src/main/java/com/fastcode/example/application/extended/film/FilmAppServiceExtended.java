package com.fastcode.example.application.extended.film;

import com.fastcode.example.application.core.film.FilmAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.extended.film.IFilmRepositoryExtended;
import com.fastcode.example.domain.extended.language.ILanguageRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("filmAppServiceExtended")
public class FilmAppServiceExtended extends FilmAppService implements IFilmAppServiceExtended {

    public FilmAppServiceExtended(
        IFilmRepositoryExtended filmRepositoryExtended,
        ILanguageRepositoryExtended languageRepositoryExtended,
        IFilmMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(filmRepositoryExtended, languageRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
