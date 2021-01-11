package com.fastcode.example.restcontrollers.extended;

import com.fastcode.example.application.extended.address.IAddressAppServiceExtended;
import com.fastcode.example.application.extended.customer.ICustomerAppServiceExtended;
import com.fastcode.example.application.extended.payment.IPaymentAppServiceExtended;
import com.fastcode.example.application.extended.rental.IRentalAppServiceExtended;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.restcontrollers.core.CustomerController;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/extended")
public class CustomerControllerExtended extends CustomerController {

    public CustomerControllerExtended(
        ICustomerAppServiceExtended customerAppServiceExtended,
        IAddressAppServiceExtended addressAppServiceExtended,
        IPaymentAppServiceExtended paymentAppServiceExtended,
        IRentalAppServiceExtended rentalAppServiceExtended,
        LoggingHelper helper,
        Environment env
    ) {
        super(
            customerAppServiceExtended,
            addressAppServiceExtended,
            paymentAppServiceExtended,
            rentalAppServiceExtended,
            helper,
            env
        );
    }
    //Add your custom code here

}
