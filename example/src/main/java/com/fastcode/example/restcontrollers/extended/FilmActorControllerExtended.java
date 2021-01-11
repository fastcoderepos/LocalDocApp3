package com.fastcode.example.restcontrollers.extended;

import com.fastcode.example.application.extended.actor.IActorAppServiceExtended;
import com.fastcode.example.application.extended.film.IFilmAppServiceExtended;
import com.fastcode.example.application.extended.filmactor.IFilmActorAppServiceExtended;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.restcontrollers.core.FilmActorController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filmActor/extended")
public class FilmActorControllerExtended extends FilmActorController {

    public FilmActorControllerExtended(
        IFilmActorAppServiceExtended filmActorAppServiceExtended,
        IActorAppServiceExtended actorAppServiceExtended,
        IFilmAppServiceExtended filmAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(filmActorAppServiceExtended, actorAppServiceExtended, filmAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
