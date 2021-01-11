package com.fastcode.example.application.extended.filmcategory;

import com.fastcode.example.application.core.filmcategory.FilmCategoryAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.extended.category.ICategoryRepositoryExtended;
import com.fastcode.example.domain.extended.film.IFilmRepositoryExtended;
import com.fastcode.example.domain.extended.filmcategory.IFilmCategoryRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("filmCategoryAppServiceExtended")
public class FilmCategoryAppServiceExtended extends FilmCategoryAppService implements IFilmCategoryAppServiceExtended {

    public FilmCategoryAppServiceExtended(
        IFilmCategoryRepositoryExtended filmCategoryRepositoryExtended,
        ICategoryRepositoryExtended categoryRepositoryExtended,
        IFilmRepositoryExtended filmRepositoryExtended,
        IFilmCategoryMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(filmCategoryRepositoryExtended, categoryRepositoryExtended, filmRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
