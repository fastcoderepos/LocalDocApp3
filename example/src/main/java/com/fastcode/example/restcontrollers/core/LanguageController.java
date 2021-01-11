package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.application.core.film.IFilmAppService;
import com.fastcode.example.application.core.film.dto.FindFilmByIdOutput;
import com.fastcode.example.application.core.language.ILanguageAppService;
import com.fastcode.example.application.core.language.dto.*;
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
@RequestMapping("/language")
@RequiredArgsConstructor
public class LanguageController {

    @Qualifier("languageAppService")
    @NonNull
    protected final ILanguageAppService _languageAppService;

    @Qualifier("filmAppService")
    @NonNull
    protected final IFilmAppService _filmAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('LANGUAGEENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateLanguageOutput> create(@RequestBody @Valid CreateLanguageInput language) {
        CreateLanguageOutput output = _languageAppService.create(language);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete language ------------
    @PreAuthorize("hasAnyAuthority('LANGUAGEENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        FindLanguageByIdOutput output = _languageAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a language with a id=%s", id))
            );

        _languageAppService.delete(Integer.valueOf(id));
    }

    // ------------ Update language ------------
    @PreAuthorize("hasAnyAuthority('LANGUAGEENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateLanguageOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateLanguageInput language
    ) {
        FindLanguageByIdOutput currentLanguage = _languageAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(currentLanguage)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Unable to update. Language with id=%s not found.", id))
            );

        language.setVersiono(currentLanguage.getVersiono());
        UpdateLanguageOutput output = _languageAppService.update(Integer.valueOf(id), language);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('LANGUAGEENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindLanguageByIdOutput> findById(@PathVariable String id) {
        FindLanguageByIdOutput output = _languageAppService.findById(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('LANGUAGEENTITY_READ')")
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
            sort = Sort.by(Sort.Direction.ASC, "languageId");
        }

        Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

        return ResponseEntity.ok(_languageAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('LANGUAGEENTITY_READ')")
    @RequestMapping(
        value = "/{id}/films",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity getFilms(
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
            sort = Sort.by(Sort.Direction.ASC, "filmId");
        }

        Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
        Map<String, String> joinColDetails = _languageAppService.parseFilmsJoinColumn(id);
        Optional
            .ofNullable(joinColDetails)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid join column")));

        searchCriteria.setJoinColumns(joinColDetails);

        List<FindFilmByIdOutput> output = _filmAppService.find(searchCriteria, pageable);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
