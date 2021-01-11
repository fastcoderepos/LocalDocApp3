package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.application.core.category.ICategoryAppService;
import com.fastcode.example.application.core.category.dto.*;
import com.fastcode.example.application.core.filmcategory.IFilmCategoryAppService;
import com.fastcode.example.application.core.filmcategory.dto.FindFilmCategoryByIdOutput;
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
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    @Qualifier("categoryAppService")
    @NonNull
    protected final ICategoryAppService _categoryAppService;

    @Qualifier("filmCategoryAppService")
    @NonNull
    protected final IFilmCategoryAppService _filmCategoryAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('CATEGORYENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateCategoryOutput> create(@RequestBody @Valid CreateCategoryInput category) {
        CreateCategoryOutput output = _categoryAppService.create(category);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete category ------------
    @PreAuthorize("hasAnyAuthority('CATEGORYENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        FindCategoryByIdOutput output = _categoryAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a category with a id=%s", id))
            );

        _categoryAppService.delete(Integer.valueOf(id));
    }

    // ------------ Update category ------------
    @PreAuthorize("hasAnyAuthority('CATEGORYENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateCategoryOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateCategoryInput category
    ) {
        FindCategoryByIdOutput currentCategory = _categoryAppService.findById(Integer.valueOf(id));
        Optional
            .ofNullable(currentCategory)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Unable to update. Category with id=%s not found.", id))
            );

        category.setVersiono(currentCategory.getVersiono());
        UpdateCategoryOutput output = _categoryAppService.update(Integer.valueOf(id), category);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CATEGORYENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindCategoryByIdOutput> findById(@PathVariable String id) {
        FindCategoryByIdOutput output = _categoryAppService.findById(Integer.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('CATEGORYENTITY_READ')")
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

        return ResponseEntity.ok(_categoryAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('CATEGORYENTITY_READ')")
    @RequestMapping(
        value = "/{id}/filmCategorys",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity getFilmCategorys(
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
            sort = Sort.by(Sort.Direction.ASC, "categoryId");
        }

        Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
        Map<String, String> joinColDetails = _categoryAppService.parseFilmCategorysJoinColumn(id);
        Optional
            .ofNullable(joinColDetails)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid join column")));

        searchCriteria.setJoinColumns(joinColDetails);

        List<FindFilmCategoryByIdOutput> output = _filmCategoryAppService.find(searchCriteria, pageable);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
