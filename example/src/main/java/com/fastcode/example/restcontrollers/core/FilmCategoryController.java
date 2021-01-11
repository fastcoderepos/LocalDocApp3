package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.application.core.category.ICategoryAppService;
import com.fastcode.example.application.core.film.IFilmAppService;
import com.fastcode.example.application.core.filmcategory.IFilmCategoryAppService;
import com.fastcode.example.application.core.filmcategory.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.OffsetBasedPageRequest;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.domain.core.filmcategory.FilmCategoryId;
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
@RequestMapping("/filmCategory")
@RequiredArgsConstructor
public class FilmCategoryController {

    @Qualifier("filmCategoryAppService")
    @NonNull
    protected final IFilmCategoryAppService _filmCategoryAppService;

    @Qualifier("categoryAppService")
    @NonNull
    protected final ICategoryAppService _categoryAppService;

    @Qualifier("filmAppService")
    @NonNull
    protected final IFilmAppService _filmAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('FILMCATEGORYENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateFilmCategoryOutput> create(@RequestBody @Valid CreateFilmCategoryInput filmCategory) {
        CreateFilmCategoryOutput output = _filmCategoryAppService.create(filmCategory);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete filmCategory ------------
    @PreAuthorize("hasAnyAuthority('FILMCATEGORYENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        FilmCategoryId filmcategoryid = _filmCategoryAppService.parseFilmCategoryKey(id);
        Optional
            .ofNullable(filmcategoryid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindFilmCategoryByIdOutput output = _filmCategoryAppService.findById(filmcategoryid);
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a filmCategory with a id=%s", id))
            );

        _filmCategoryAppService.delete(filmcategoryid);
    }

    // ------------ Update filmCategory ------------
    @PreAuthorize("hasAnyAuthority('FILMCATEGORYENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateFilmCategoryOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateFilmCategoryInput filmCategory
    ) {
        FilmCategoryId filmcategoryid = _filmCategoryAppService.parseFilmCategoryKey(id);

        Optional
            .ofNullable(filmcategoryid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindFilmCategoryByIdOutput currentFilmCategory = _filmCategoryAppService.findById(filmcategoryid);
        Optional
            .ofNullable(currentFilmCategory)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("Unable to update. FilmCategory with id=%s not found.", id)
                    )
            );

        filmCategory.setVersiono(currentFilmCategory.getVersiono());
        UpdateFilmCategoryOutput output = _filmCategoryAppService.update(filmcategoryid, filmCategory);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('FILMCATEGORYENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindFilmCategoryByIdOutput> findById(@PathVariable String id) {
        FilmCategoryId filmcategoryid = _filmCategoryAppService.parseFilmCategoryKey(id);
        Optional
            .ofNullable(filmcategoryid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindFilmCategoryByIdOutput output = _filmCategoryAppService.findById(filmcategoryid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('FILMCATEGORYENTITY_READ')")
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
            sort = Sort.by(Sort.Direction.ASC, "categoryId");
        }

        Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

        return ResponseEntity.ok(_filmCategoryAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('FILMCATEGORYENTITY_READ')")
    @RequestMapping(
        value = "/{id}/category",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetCategoryOutput> getCategory(@PathVariable String id) {
        FilmCategoryId filmcategoryid = _filmCategoryAppService.parseFilmCategoryKey(id);
        Optional
            .ofNullable(filmcategoryid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        GetCategoryOutput output = _filmCategoryAppService.getCategory(filmcategoryid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('FILMCATEGORYENTITY_READ')")
    @RequestMapping(
        value = "/{id}/film",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetFilmOutput> getFilm(@PathVariable String id) {
        FilmCategoryId filmcategoryid = _filmCategoryAppService.parseFilmCategoryKey(id);
        Optional
            .ofNullable(filmcategoryid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        GetFilmOutput output = _filmCategoryAppService.getFilm(filmcategoryid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
