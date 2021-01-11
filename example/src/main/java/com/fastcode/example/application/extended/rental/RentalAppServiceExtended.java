package com.fastcode.example.application.extended.rental;

import com.fastcode.example.application.core.rental.RentalAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.extended.customer.ICustomerRepositoryExtended;
import com.fastcode.example.domain.extended.inventory.IInventoryRepositoryExtended;
import com.fastcode.example.domain.extended.rental.IRentalRepositoryExtended;
import com.fastcode.example.domain.extended.staff.IStaffRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("rentalAppServiceExtended")
public class RentalAppServiceExtended extends RentalAppService implements IRentalAppServiceExtended {

    public RentalAppServiceExtended(
        IRentalRepositoryExtended rentalRepositoryExtended,
        ICustomerRepositoryExtended customerRepositoryExtended,
        IInventoryRepositoryExtended inventoryRepositoryExtended,
        IStaffRepositoryExtended staffRepositoryExtended,
        IRentalMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(
            rentalRepositoryExtended,
            customerRepositoryExtended,
            inventoryRepositoryExtended,
            staffRepositoryExtended,
            mapper,
            logHelper
        );
    }
    //Add your custom code here

}
