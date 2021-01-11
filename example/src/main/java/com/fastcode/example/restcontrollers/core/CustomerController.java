package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.application.core.address.IAddressAppService;
import com.fastcode.example.application.core.customer.ICustomerAppService;
import com.fastcode.example.application.core.customer.dto.*;
import com.fastcode.example.application.core.payment.IPaymentAppService;
import com.fastcode.example.application.core.payment.dto.FindPaymentByIdOutput;
import com.fastcode.example.application.core.rental.IRentalAppService;
import com.fastcode.example.application.core.rental.dto.FindRentalByIdOutput;
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
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    @Qualifier("customerAppService")
    @NonNull
    protected final ICustomerAppService _customerAppService;

    @Qualifier("addressAppService")
    @NonNull
    protected final IAddressAppService _addressAppService;

    @Qualifier("paymentAppService")
    @NonNull
    protected final IPaymentAppService _paymentAppService;

    @Qualifier("rentalAppService")
    @NonNull
    protected final IRentalAppService _rentalAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('CUSTOMERENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateCustomerOutput> create(@RequestBody @Valid CreateCustomerInput customer) {
        CreateCustomerOutput output = _customerAppService.create(customer);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete customer ------------
    @PreAuthorize("hasAnyAuthority('CUSTOMERENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        FindCustomerByIdOutput output = _customerAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a customer with a id=%s", id))
            );

        _customerAppService.delete(Integer.valueOf(id));
    }

    // ------------ Update customer ------------
    @PreAuthorize("hasAnyAuthority('CUSTOMERENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateCustomerOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateCustomerInput customer
    ) {
        FindCustomerByIdOutput currentCustomer = _customerAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(currentCustomer)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Unable to update. Customer with id=%s not found.", id))
            );

        customer.setVersiono(currentCustomer.getVersiono());
        UpdateCustomerOutput output = _customerAppService.update(Integer.valueOf(id), customer);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMERENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindCustomerByIdOutput> findById(@PathVariable String id) {
        FindCustomerByIdOutput output = _customerAppService.findById(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMERENTITY_READ')")
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
            sort = Sort.by(Sort.Direction.ASC, "customerId");
        }

        Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

        return ResponseEntity.ok(_customerAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMERENTITY_READ')")
    @RequestMapping(
        value = "/{id}/address",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetAddressOutput> getAddress(@PathVariable String id) {
        GetAddressOutput output = _customerAppService.getAddress(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMERENTITY_READ')")
    @RequestMapping(
        value = "/{id}/payments",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity getPayments(
        @PathVariable String id,
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

        Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
        Map<String, String> joinColDetails = _customerAppService.parsePaymentsJoinColumn(id);
        Optional
            .ofNullable(joinColDetails)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid join column")));

        searchCriteria.setJoinColumns(joinColDetails);

        List<FindPaymentByIdOutput> output = _paymentAppService.find(searchCriteria, pageable);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMERENTITY_READ')")
    @RequestMapping(
        value = "/{id}/rentals",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity getRentals(
        @PathVariable String id,
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
            sort = Sort.by(Sort.Direction.ASC, "rentalId");
        }

        Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
        Map<String, String> joinColDetails = _customerAppService.parseRentalsJoinColumn(id);
        Optional
            .ofNullable(joinColDetails)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid join column")));

        searchCriteria.setJoinColumns(joinColDetails);

        List<FindRentalByIdOutput> output = _rentalAppService.find(searchCriteria, pageable);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
