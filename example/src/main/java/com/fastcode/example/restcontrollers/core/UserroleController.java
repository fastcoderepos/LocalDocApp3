package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.application.core.authorization.role.IRoleAppService;
import com.fastcode.example.application.core.authorization.user.IUserAppService;
import com.fastcode.example.application.core.authorization.user.dto.FindUserByIdOutput;
import com.fastcode.example.application.core.authorization.userrole.IUserroleAppService;
import com.fastcode.example.application.core.authorization.userrole.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.OffsetBasedPageRequest;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.domain.core.authorization.userrole.UserroleId;
import com.fastcode.example.security.JWTAppService;
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
@RequestMapping("/userrole")
@RequiredArgsConstructor
public class UserroleController {

    @Qualifier("userroleAppService")
    @NonNull
    protected final IUserroleAppService _userroleAppService;

    @Qualifier("roleAppService")
    @NonNull
    protected final IRoleAppService _roleAppService;

    @Qualifier("userAppService")
    @NonNull
    protected final IUserAppService _userAppService;

    @NonNull
    protected final JWTAppService _jwtAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('USERROLEENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateUserroleOutput> create(@RequestBody @Valid CreateUserroleInput userrole) {
        CreateUserroleOutput output = _userroleAppService.create(userrole);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        FindUserByIdOutput foundUser = _userAppService.findById(output.getUserId());
        _jwtAppService.deleteAllUserTokens(foundUser.getUserName());

        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete userrole ------------
    @PreAuthorize("hasAnyAuthority('USERROLEENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        UserroleId userroleid = _userroleAppService.parseUserroleKey(id);
        Optional
            .ofNullable(userroleid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindUserroleByIdOutput output = _userroleAppService.findById(userroleid);
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a userrole with a id=%s", id))
            );

        _userroleAppService.delete(userroleid);
        FindUserByIdOutput foundUser = _userAppService.findById(output.getUserId());
        _jwtAppService.deleteAllUserTokens(foundUser.getUserName());
    }

    // ------------ Update userrole ------------
    @PreAuthorize("hasAnyAuthority('USERROLEENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateUserroleOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateUserroleInput userrole
    ) {
        UserroleId userroleid = _userroleAppService.parseUserroleKey(id);

        Optional
            .ofNullable(userroleid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindUserroleByIdOutput currentUserrole = _userroleAppService.findById(userroleid);
        Optional
            .ofNullable(currentUserrole)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Unable to update. Userrole with id=%s not found.", id))
            );

        userrole.setVersiono(currentUserrole.getVersiono());
        FindUserByIdOutput foundUser = _userAppService.findById(currentUserrole.getUserId());
        _jwtAppService.deleteAllUserTokens(foundUser.getUserName());

        UpdateUserroleOutput output = _userroleAppService.update(userroleid, userrole);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USERROLEENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindUserroleByIdOutput> findById(@PathVariable String id) {
        UserroleId userroleid = _userroleAppService.parseUserroleKey(id);
        Optional
            .ofNullable(userroleid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindUserroleByIdOutput output = _userroleAppService.findById(userroleid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USERROLEENTITY_READ')")
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

        Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

        return ResponseEntity.ok(_userroleAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('USERROLEENTITY_READ')")
    @RequestMapping(
        value = "/{id}/role",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetRoleOutput> getRole(@PathVariable String id) {
        UserroleId userroleid = _userroleAppService.parseUserroleKey(id);
        Optional
            .ofNullable(userroleid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        GetRoleOutput output = _userroleAppService.getRole(userroleid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USERROLEENTITY_READ')")
    @RequestMapping(
        value = "/{id}/user",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetUserOutput> getUser(@PathVariable String id) {
        UserroleId userroleid = _userroleAppService.parseUserroleKey(id);
        Optional
            .ofNullable(userroleid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        GetUserOutput output = _userroleAppService.getUser(userroleid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
