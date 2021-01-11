package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.application.core.film.IFilmAppService;
import com.fastcode.example.application.core.inventory.IInventoryAppService;
import com.fastcode.example.application.core.inventory.dto.*;
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
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    @Qualifier("inventoryAppService")
    @NonNull
    protected final IInventoryAppService _inventoryAppService;

    @Qualifier("filmAppService")
    @NonNull
    protected final IFilmAppService _filmAppService;

    @Qualifier("rentalAppService")
    @NonNull
    protected final IRentalAppService _rentalAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateInventoryOutput> create(@RequestBody @Valid CreateInventoryInput inventory) {
        CreateInventoryOutput output = _inventoryAppService.create(inventory);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete inventory ------------
    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        FindInventoryByIdOutput output = _inventoryAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a inventory with a id=%s", id))
            );

        _inventoryAppService.delete(Integer.valueOf(id));
    }

    // ------------ Update inventory ------------
    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateInventoryOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateInventoryInput inventory
    ) {
        FindInventoryByIdOutput currentInventory = _inventoryAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(currentInventory)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(String.format("Unable to update. Inventory with id=%s not found.", id))
            );

        inventory.setVersiono(currentInventory.getVersiono());
        UpdateInventoryOutput output = _inventoryAppService.update(Integer.valueOf(id), inventory);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindInventoryByIdOutput> findById(@PathVariable String id) {
        FindInventoryByIdOutput output = _inventoryAppService.findById(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_READ')")
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
            sort = Sort.by(Sort.Direction.ASC, "inventoryId");
        }

        Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

        return ResponseEntity.ok(_inventoryAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_READ')")
    @RequestMapping(
        value = "/{id}/film",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetFilmOutput> getFilm(@PathVariable String id) {
        GetFilmOutput output = _inventoryAppService.getFilm(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('INVENTORYENTITY_READ')")
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
        Map<String, String> joinColDetails = _inventoryAppService.parseRentalsJoinColumn(id);
        Optional
            .ofNullable(joinColDetails)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid join column")));

        searchCriteria.setJoinColumns(joinColDetails);

        List<FindRentalByIdOutput> output = _rentalAppService.find(searchCriteria, pageable);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
