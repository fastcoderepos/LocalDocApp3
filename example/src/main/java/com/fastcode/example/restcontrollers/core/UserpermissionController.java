package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.application.core.authorization.permission.IPermissionAppService;
import com.fastcode.example.application.core.authorization.user.IUserAppService;
import com.fastcode.example.application.core.authorization.user.dto.FindUserByIdOutput;
import com.fastcode.example.application.core.authorization.userpermission.IUserpermissionAppService;
import com.fastcode.example.application.core.authorization.userpermission.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.OffsetBasedPageRequest;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.domain.core.authorization.userpermission.UserpermissionId;
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
@RequestMapping("/userpermission")
@RequiredArgsConstructor
public class UserpermissionController {

    @Qualifier("userpermissionAppService")
    @NonNull
    protected final IUserpermissionAppService _userpermissionAppService;

    @Qualifier("permissionAppService")
    @NonNull
    protected final IPermissionAppService _permissionAppService;

    @Qualifier("userAppService")
    @NonNull
    protected final IUserAppService _userAppService;

    @NonNull
    protected final JWTAppService _jwtAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('USERPERMISSIONENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateUserpermissionOutput> create(
        @RequestBody @Valid CreateUserpermissionInput userpermission
    ) {
        CreateUserpermissionOutput output = _userpermissionAppService.create(userpermission);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));

        FindUserByIdOutput foundUser = _userAppService.findById(output.getUserId());
        _jwtAppService.deleteAllUserTokens(foundUser.getUserName());

        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete userpermission ------------
    @PreAuthorize("hasAnyAuthority('USERPERMISSIONENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        UserpermissionId userpermissionid = _userpermissionAppService.parseUserpermissionKey(id);
        Optional
            .ofNullable(userpermissionid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindUserpermissionByIdOutput output = _userpermissionAppService.findById(userpermissionid);
        Optional
            .ofNullable(output)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(String.format("There does not exist a userpermission with a id=%s", id))
            );

        _userpermissionAppService.delete(userpermissionid);
        FindUserByIdOutput foundUser = _userAppService.findById(output.getUserId());
        _jwtAppService.deleteAllUserTokens(foundUser.getUserName());
    }

    // ------------ Update userpermission ------------
    @PreAuthorize("hasAnyAuthority('USERPERMISSIONENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateUserpermissionOutput> update(
        @PathVariable String id,
        @RequestBody @Valid UpdateUserpermissionInput userpermission
    ) {
        UserpermissionId userpermissionid = _userpermissionAppService.parseUserpermissionKey(id);

        Optional
            .ofNullable(userpermissionid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindUserpermissionByIdOutput currentUserpermission = _userpermissionAppService.findById(userpermissionid);
        Optional
            .ofNullable(currentUserpermission)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        String.format("Unable to update. Userpermission with id=%s not found.", id)
                    )
            );

        userpermission.setVersiono(currentUserpermission.getVersiono());
        FindUserByIdOutput foundUser = _userAppService.findById(currentUserpermission.getUserId());
        _jwtAppService.deleteAllUserTokens(foundUser.getUserName());

        UpdateUserpermissionOutput output = _userpermissionAppService.update(userpermissionid, userpermission);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("No record found")));
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USERPERMISSIONENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindUserpermissionByIdOutput> findById(@PathVariable String id) {
        UserpermissionId userpermissionid = _userpermissionAppService.parseUserpermissionKey(id);
        Optional
            .ofNullable(userpermissionid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        FindUserpermissionByIdOutput output = _userpermissionAppService.findById(userpermissionid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USERPERMISSIONENTITY_READ')")
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

        return ResponseEntity.ok(_userpermissionAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('USERPERMISSIONENTITY_READ')")
    @RequestMapping(
        value = "/{id}/permission",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetPermissionOutput> getPermission(@PathVariable String id) {
        UserpermissionId userpermissionid = _userpermissionAppService.parseUserpermissionKey(id);
        Optional
            .ofNullable(userpermissionid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        GetPermissionOutput output = _userpermissionAppService.getPermission(userpermissionid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USERPERMISSIONENTITY_READ')")
    @RequestMapping(
        value = "/{id}/user",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<GetUserOutput> getUser(@PathVariable String id) {
        UserpermissionId userpermissionid = _userpermissionAppService.parseUserpermissionKey(id);
        Optional
            .ofNullable(userpermissionid)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid id=%s", id)));

        GetUserOutput output = _userpermissionAppService.getUser(userpermissionid);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
