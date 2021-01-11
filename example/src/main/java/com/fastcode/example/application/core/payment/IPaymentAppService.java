package com.fastcode.example.application.core.payment;

import com.fastcode.example.application.core.payment.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IPaymentAppService {
    //CRUD Operations

    CreatePaymentOutput create(CreatePaymentInput payment);

    void delete(Integer id);

    UpdatePaymentOutput update(Integer id, UpdatePaymentInput input);

    FindPaymentByIdOutput findById(Integer id);

    List<FindPaymentByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations
    //Relationship Operations
    //Relationship Operations

    GetCustomerOutput getCustomer(Integer paymentid);

    GetRentalOutput getRental(Integer paymentid);

    GetStaffOutput getStaff(Integer paymentid);
    //Join Column Parsers
}
