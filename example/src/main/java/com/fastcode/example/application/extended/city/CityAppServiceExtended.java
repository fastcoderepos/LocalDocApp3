package com.fastcode.example.application.extended.city;

import com.fastcode.example.application.core.city.CityAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.extended.city.ICityRepositoryExtended;
import com.fastcode.example.domain.extended.country.ICountryRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("cityAppServiceExtended")
public class CityAppServiceExtended extends CityAppService implements ICityAppServiceExtended {

    public CityAppServiceExtended(
        ICityRepositoryExtended cityRepositoryExtended,
        ICountryRepositoryExtended countryRepositoryExtended,
        ICityMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(cityRepositoryExtended, countryRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
