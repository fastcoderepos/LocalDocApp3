package com.fastcode.example.application.extended.address;

import com.fastcode.example.application.core.address.AddressAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.extended.address.IAddressRepositoryExtended;
import com.fastcode.example.domain.extended.city.ICityRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("addressAppServiceExtended")
public class AddressAppServiceExtended extends AddressAppService implements IAddressAppServiceExtended {

    public AddressAppServiceExtended(
        IAddressRepositoryExtended addressRepositoryExtended,
        ICityRepositoryExtended cityRepositoryExtended,
        IAddressMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(addressRepositoryExtended, cityRepositoryExtended, mapper, logHelper);
    }
    //Add your custom code here

}
