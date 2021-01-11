package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.application.core.customer.ICustomerAppService;
import com.fastcode.example.application.core.payment.IPaymentAppService;
import com.fastcode.example.application.core.payment.dto.*;
import com.fastcode.example.application.core.rental.IRentalAppService;
import com.fastcode.example.application.core.staff.IStaffAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.OffsetBasedPageRequest;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.commons.search.SearchUtils;
import java.time.*;
import java.util.*;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    @Qualifier("paymentAppService")
    @NonNull
    protected final IPaymentAppService _paymentAppService;

    @Qualifier("customerAppService")
    @NonNull
    protected final ICustomerAppService _customerAppService;

    @Qualifier("rentalAppService")
    @NonNull
    protected final IRentalAppService _rentalAppService;

    @Qualifier("staffAppService")
    @NonNull
    protected final IStaffAppService _staffAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('PAYMENTENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreatePaymentOutput> create(@RequestBody @Valid CreatePaymentInput payment) {
        CreatePaymentOutput output = _paymentAppService.create(payment);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete payment ------------
    @PreAuthorize("hasAnyAuthority('PAYMENTENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        FindPaymentByIdOutput output = _paymentAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a payment with a id=%s", id))
            );

        _paymentAppService.delete(Integer.valueOf(id));
    }

    // ------------ Update payment ------------
    @PreAuthorize("hasAnyAuthority('PAYMENTENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdatePaymentOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdatePaymentInput payment
    ) {
        FindPaymentByIdOutput currentPayment = _paymentAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(currentPayment)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Unable to update. Payment with id=%s not found.", id))
            );

        payment.setVersiono(currentPayment.getVersiono());
        UpdatePaymentOutput output = _paymentAppService.update(Integer.valueOf(id), payment);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('PAYMENTENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindPaymentByIdOutput> findById(@PathVariable String id) {
        FindPaymentByIdOutput output = _paymentAppService.findById(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('PAYMENTENTITY_READ')")
    @RequestMapping(method = RequestMethod.GET, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity find(
        @RequestParam(value = "search", required = false) String search,
        @RequestParam(value = "offset", required = false) String offset,
        @RequestParam(value = "limit", required = false) String limit,
        Sort sort
    ) throws Exception {
        if (offset == null) {
            offset = env.getProperty("fastCode.offset.default");
        }
        if (limit == null) {
            limit = env.getProperty("fastCode.limit.default");
        }

        if (sort == null || sort.isEmpty()) {
            sort = Sort.by(Sort.Direction.ASC, "paymentId");
        }

        Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

        return ResponseEntity.ok(_paymentAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('PAYMENTENTITY_READ')")
    @RequestMapping(
        value = "/{id}/customer",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetCustomerOutput> getCustomer(@PathVariable String id) {
        GetCustomerOutput output = _paymentAppService.getCustomer(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('PAYMENTENTITY_READ')")
    @RequestMapping(
        value = "/{id}/rental",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetRentalOutput> getRental(@PathVariable String id) {
        GetRentalOutput output = _paymentAppService.getRental(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('PAYMENTENTITY_READ')")
    @RequestMapping(
        value = "/{id}/staff",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetStaffOutput> getStaff(@PathVariable String id) {
        GetStaffOutput output = _paymentAppService.getStaff(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
