package com.fastcode.example.restcontrollers.extended;

import com.fastcode.example.application.extended.film.IFilmAppServiceExtended;
import com.fastcode.example.application.extended.language.ILanguageAppServiceExtended;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.restcontrollers.core.LanguageController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/language/extended")
public class LanguageControllerExtended extends LanguageController {

    public LanguageControllerExtended(
        ILanguageAppServiceExtended languageAppServiceExtended,
        IFilmAppServiceExtended filmAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(languageAppServiceExtended, filmAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
