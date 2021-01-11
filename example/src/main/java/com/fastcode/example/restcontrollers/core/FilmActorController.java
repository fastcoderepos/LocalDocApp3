package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.application.core.actor.IActorAppService;
import com.fastcode.example.application.core.film.IFilmAppService;
import com.fastcode.example.application.core.filmactor.IFilmActorAppService;
import com.fastcode.example.application.core.filmactor.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.OffsetBasedPageRequest;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.domain.core.filmactor.FilmActorId;
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
@RequestMapping("/filmActor")
@RequiredArgsConstructor
public class FilmActorController {

    @Qualifier("filmActorAppService")
    @NonNull
    protected final IFilmActorAppService _filmActorAppService;

    @Qualifier("actorAppService")
    @NonNull
    protected final IActorAppService _actorAppService;

    @Qualifier("filmAppService")
    @NonNull
    protected final IFilmAppService _filmAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('FILMACTORENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateFilmActorOutput> create(@RequestBody @Valid CreateFilmActorInput filmActor) {
        CreateFilmActorOutput output = _filmActorAppService.create(filmActor);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete filmActor ------------
    @PreAuthorize("hasAnyAuthority('FILMACTORENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        FilmActorId filmactorid = _filmActorAppService.parseFilmActorKey(id);
        Optional
            .ofNullable(filmactorid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindFilmActorByIdOutput output = _filmActorAppService.findById(filmactorid);
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a filmActor with a id=%s", id))
            );

        _filmActorAppService.delete(filmactorid);
    }

    // ------------ Update filmActor ------------
    @PreAuthorize("hasAnyAuthority('FILMACTORENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateFilmActorOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateFilmActorInput filmActor
    ) {
        FilmActorId filmactorid = _filmActorAppService.parseFilmActorKey(id);

        Optional
            .ofNullable(filmactorid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindFilmActorByIdOutput currentFilmActor = _filmActorAppService.findById(filmactorid);
        Optional
            .ofNullable(currentFilmActor)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(String.format("Unable to update. FilmActor with id=%s not found.", id))
            );

        filmActor.setVersiono(currentFilmActor.getVersiono());
        UpdateFilmActorOutput output = _filmActorAppService.update(filmactorid, filmActor);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('FILMACTORENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindFilmActorByIdOutput> findById(@PathVariable String id) {
        FilmActorId filmactorid = _filmActorAppService.parseFilmActorKey(id);
        Optional
            .ofNullable(filmactorid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindFilmActorByIdOutput output = _filmActorAppService.findById(filmactorid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('FILMACTORENTITY_READ')")
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

        return ResponseEntity.ok(_filmActorAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('FILMACTORENTITY_READ')")
    @RequestMapping(
        value = "/{id}/actor",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetActorOutput> getActor(@PathVariable String id) {
        FilmActorId filmactorid = _filmActorAppService.parseFilmActorKey(id);
        Optional
            .ofNullable(filmactorid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        GetActorOutput output = _filmActorAppService.getActor(filmactorid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('FILMACTORENTITY_READ')")
    @RequestMapping(
        value = "/{id}/film",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetFilmOutput> getFilm(@PathVariable String id) {
        FilmActorId filmactorid = _filmActorAppService.parseFilmActorKey(id);
        Optional
            .ofNullable(filmactorid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        GetFilmOutput output = _filmActorAppService.getFilm(filmactorid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
