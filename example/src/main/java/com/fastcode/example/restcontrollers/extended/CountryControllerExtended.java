package com.fastcode.example.restcontrollers.extended;

import com.fastcode.example.application.extended.city.ICityAppServiceExtended;
import com.fastcode.example.application.extended.country.ICountryAppServiceExtended;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.restcontrollers.core.CountryController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/country/extended")
public class CountryControllerExtended extends CountryController {

    public CountryControllerExtended(
        ICountryAppServiceExtended countryAppServiceExtended,
        ICityAppServiceExtended cityAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(countryAppServiceExtended, cityAppServiceExtended, helper, env);
    }
    //Add your custom code here

}
