package com.fastcode.example.application.extended.country;

import com.fastcode.example.application.core.country.CountryAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.extended.country.ICountryRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("countryAppServiceExtended")
public class CountryAppServiceExtended extends CountryAppService implements ICountryAppServiceExtended {

    public CountryAppServiceExtended(
        ICountryRepositoryExtended countryRepositoryExtended,
        ICountryMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(countryRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
