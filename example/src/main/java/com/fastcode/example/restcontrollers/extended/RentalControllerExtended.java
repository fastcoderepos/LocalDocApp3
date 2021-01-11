package com.fastcode.example.restcontrollers.extended;

import com.fastcode.example.application.extended.customer.ICustomerAppServiceExtended;
import com.fastcode.example.application.extended.inventory.IInventoryAppServiceExtended;
import com.fastcode.example.application.extended.payment.IPaymentAppServiceExtended;
import com.fastcode.example.application.extended.rental.IRentalAppServiceExtended;
import com.fastcode.example.application.extended.staff.IStaffAppServiceExtended;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.restcontrollers.core.RentalController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rental/extended")
public class RentalControllerExtended extends RentalController {

    public RentalControllerExtended(
        IRentalAppServiceExtended rentalAppServiceExtended,
        ICustomerAppServiceExtended customerAppServiceExtended,
        IInventoryAppServiceExtended inventoryAppServiceExtended,
        IPaymentAppServiceExtended paymentAppServiceExtended,
        IStaffAppServiceExtended staffAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(
            rentalAppServiceExtended,
            customerAppServiceExtended,
            inventoryAppServiceExtended,
            paymentAppServiceExtended,
            staffAppServiceExtended,
            helper,
            env
        );
    }
    //Add your custom code here

}
