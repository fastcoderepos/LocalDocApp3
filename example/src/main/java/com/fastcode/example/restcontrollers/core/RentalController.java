package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.application.core.customer.ICustomerAppService;
import com.fastcode.example.application.core.inventory.IInventoryAppService;
import com.fastcode.example.application.core.payment.IPaymentAppService;
import com.fastcode.example.application.core.payment.dto.FindPaymentByIdOutput;
import com.fastcode.example.application.core.rental.IRentalAppService;
import com.fastcode.example.application.core.rental.dto.*;
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
@RequestMapping("/rental")
@RequiredArgsConstructor
public class RentalController {

    @Qualifier("rentalAppService")
    @NonNull
    protected final IRentalAppService _rentalAppService;

    @Qualifier("customerAppService")
    @NonNull
    protected final ICustomerAppService _customerAppService;

    @Qualifier("inventoryAppService")
    @NonNull
    protected final IInventoryAppService _inventoryAppService;

    @Qualifier("paymentAppService")
    @NonNull
    protected final IPaymentAppService _paymentAppService;

    @Qualifier("staffAppService")
    @NonNull
    protected final IStaffAppService _staffAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('RENTALENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateRentalOutput> create(@RequestBody @Valid CreateRentalInput rental) {
        CreateRentalOutput output = _rentalAppService.create(rental);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete rental ------------
    @PreAuthorize("hasAnyAuthority('RENTALENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        FindRentalByIdOutput output = _rentalAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a rental with a id=%s", id))
            );

        _rentalAppService.delete(Integer.valueOf(id));
    }

    // ------------ Update rental ------------
    @PreAuthorize("hasAnyAuthority('RENTALENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateRentalOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateRentalInput rental
    ) {
        FindRentalByIdOutput currentRental = _rentalAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(currentRental)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Unable to update. Rental with id=%s not found.", id))
            );

        rental.setVersiono(currentRental.getVersiono());
        UpdateRentalOutput output = _rentalAppService.update(Integer.valueOf(id), rental);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('RENTALENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindRentalByIdOutput> findById(@PathVariable String id) {
        FindRentalByIdOutput output = _rentalAppService.findById(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('RENTALENTITY_READ')")
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
            sort = Sort.by(Sort.Direction.ASC, "rentalId");
        }

        Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

        return ResponseEntity.ok(_rentalAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('RENTALENTITY_READ')")
    @RequestMapping(
        value = "/{id}/customer",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetCustomerOutput> getCustomer(@PathVariable String id) {
        GetCustomerOutput output = _rentalAppService.getCustomer(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('RENTALENTITY_READ')")
    @RequestMapping(
        value = "/{id}/inventory",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetInventoryOutput> getInventory(@PathVariable String id) {
        GetInventoryOutput output = _rentalAppService.getInventory(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('RENTALENTITY_READ')")
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
        Map<String, String> joinColDetails = _rentalAppService.parsePaymentsJoinColumn(id);
        Optional
            .ofNullable(joinColDetails)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid join column")));

        searchCriteria.setJoinColumns(joinColDetails);

        List<FindPaymentByIdOutput> output = _paymentAppService.find(searchCriteria, pageable);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('RENTALENTITY_READ')")
    @RequestMapping(
        value = "/{id}/staff",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetStaffOutput> getStaff(@PathVariable String id) {
        GetStaffOutput output = _rentalAppService.getStaff(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
