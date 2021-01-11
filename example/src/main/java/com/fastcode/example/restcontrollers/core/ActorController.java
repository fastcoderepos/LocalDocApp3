package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.application.core.actor.IActorAppService;
import com.fastcode.example.application.core.actor.dto.*;
import com.fastcode.example.application.core.filmactor.IFilmActorAppService;
import com.fastcode.example.application.core.filmactor.dto.FindFilmActorByIdOutput;
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
@RequestMapping("/actor")
@RequiredArgsConstructor
public class ActorController {

    @Qualifier("actorAppService")
    @NonNull
    protected final IActorAppService _actorAppService;

    @Qualifier("filmActorAppService")
    @NonNull
    protected final IFilmActorAppService _filmActorAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('ACTORENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateActorOutput> create(@RequestBody @Valid CreateActorInput actor) {
        CreateActorOutput output = _actorAppService.create(actor);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete actor ------------
    @PreAuthorize("hasAnyAuthority('ACTORENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        FindActorByIdOutput output = _actorAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a actor with a id=%s", id))
            );

        _actorAppService.delete(Integer.valueOf(id));
    }

    // ------------ Update actor ------------
    @PreAuthorize("hasAnyAuthority('ACTORENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateActorOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateActorInput actor
    ) {
        FindActorByIdOutput currentActor = _actorAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(currentActor)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Unable to update. Actor with id=%s not found.", id))
            );

        actor.setVersiono(currentActor.getVersiono());
        UpdateActorOutput output = _actorAppService.update(Integer.valueOf(id), actor);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ACTORENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindActorByIdOutput> findById(@PathVariable String id) {
        FindActorByIdOutput output = _actorAppService.findById(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ACTORENTITY_READ')")
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
            sort = Sort.by(Sort.Direction.ASC, "actorId");
        }

        Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

        return ResponseEntity.ok(_actorAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('ACTORENTITY_READ')")
    @RequestMapping(
        value = "/{id}/filmActors",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity getFilmActors(
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
            sort = Sort.by(Sort.Direction.ASC, "actorId");
        }

        Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
        Map<String, String> joinColDetails = _actorAppService.parseFilmActorsJoinColumn(id);
        Optional
            .ofNullable(joinColDetails)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid join column")));

        searchCriteria.setJoinColumns(joinColDetails);

        List<FindFilmActorByIdOutput> output = _filmActorAppService.find(searchCriteria, pageable);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
