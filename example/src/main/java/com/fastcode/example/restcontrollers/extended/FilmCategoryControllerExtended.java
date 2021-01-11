package com.fastcode.example.restcontrollers.extended;

import com.fastcode.example.application.extended.category.ICategoryAppServiceExtended;
import com.fastcode.example.application.extended.film.IFilmAppServiceExtended;
import com.fastcode.example.application.extended.filmcategory.IFilmCategoryAppServiceExtended;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.restcontrollers.core.FilmCategoryController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filmCategory/extended")
public class FilmCategoryControllerExtended extends FilmCategoryController {

    public FilmCategoryControllerExtended(
        IFilmCategoryAppServiceExtended filmCategoryAppServiceExtended,
        ICategoryAppServiceExtended categoryAppServiceExtended,
        IFilmAppServiceExtended filmAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(filmCategoryAppServiceExtended, categoryAppServiceExtended, filmAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
