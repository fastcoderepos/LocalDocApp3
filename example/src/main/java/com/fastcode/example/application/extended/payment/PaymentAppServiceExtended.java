package com.fastcode.example.application.extended.payment;

import com.fastcode.example.application.core.payment.PaymentAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.extended.customer.ICustomerRepositoryExtended;
import com.fastcode.example.domain.extended.payment.IPaymentRepositoryExtended;
import com.fastcode.example.domain.extended.rental.IRentalRepositoryExtended;
import com.fastcode.example.domain.extended.staff.IStaffRepositoryExtended;
import org.springframework.stereotype.Service;

@Service("paymentAppServiceExtended")
public class PaymentAppServiceExtended extends PaymentAppService implements IPaymentAppServiceExtended {

    public PaymentAppServiceExtended(
        IPaymentRepositoryExtended paymentRepositoryExtended,
        ICustomerRepositoryExtended customerRepositoryExtended,
        IRentalRepositoryExtended rentalRepositoryExtended,
        IStaffRepositoryExtended staffRepositoryExtended,
        IPaymentMapperExtended mapper,
        LoggingHelper logHelper
    ) {
        super(
            paymentRepositoryExtended,
            customerRepositoryExtended,
            rentalRepositoryExtended,
            staffRepositoryExtended,
            mapper,
            logHelper
        );
    }
    //Add your custom code here

}
