package com.fastcode.example.restcontrollers.core;

import com.fastcode.example.application.core.authorization.user.IUserAppService;
import com.fastcode.example.application.core.authorization.user.dto.*;
import com.fastcode.example.application.core.authorization.user.dto.FindUserByIdOutput;
import com.fastcode.example.application.core.authorization.userpermission.IUserpermissionAppService;
import com.fastcode.example.application.core.authorization.userpermission.dto.FindUserpermissionByIdOutput;
import com.fastcode.example.application.core.authorization.userrole.IUserroleAppService;
import com.fastcode.example.application.core.authorization.userrole.dto.FindUserroleByIdOutput;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.OffsetBasedPageRequest;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import com.fastcode.example.security.JWTAppService;
import java.time.*;
import java.util.*;
import javax.persistence.EntityExistsException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Qualifier("userAppService")
    @NonNull
    protected final IUserAppService _userAppService;

    @Qualifier("userpermissionAppService")
    @NonNull
    protected final IUserpermissionAppService _userpermissionAppService;

    @Qualifier("userroleAppService")
    @NonNull
    protected final IUserroleAppService _userroleAppService;

    @NonNull
    protected final PasswordEncoder pEncoder;

    @NonNull
    protected final JWTAppService _jwtAppService;

    @NonNull
    protected final LoggingHelper logHelper;

    @NonNull
    protected final Environment env;

    @PreAuthorize("hasAnyAuthority('USERENTITY_CREATE')")
    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
    public ResponseEntity<CreateUserOutput> create(@RequestBody @Valid CreateUserInput user) {
        FindUserByNameOutput foundUser = _userAppService.findByUserName(user.getUserName());

        if (foundUser != null) {
            logHelper.getLogger().error("There already exists a user with a UserName=%s", user.getUserName());
            throw new EntityExistsException(
                String.format("There already exists a user with UserName =%s", user.getUserName())
            );
        }
        user.setPassword(pEncoder.encode(user.getPassword()));
        user.setIsEmailConfirmed(true);
        foundUser = _userAppService.findByEmailAddress(user.getEmailAddress());

        if (foundUser != null) {
            logHelper.getLogger().error("There already exists a user with a email =%s", user.getEmailAddress());
            throw new EntityExistsException(
                String.format("There already exists a user with email =%s", user.getEmailAddress())
            );
        }

        CreateUserOutput output = _userAppService.create(user);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    // ------------ Delete user ------------
    @PreAuthorize("hasAnyAuthority('USERENTITY_DELETE')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = { "application/json" })
    public void delete(@PathVariable String id) {
        FindUserByIdOutput output = _userAppService.findById(Long.valueOf(id));
        Optional
            .ofNullable(output)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("There does not exist a user with a id=%s", id))
            );

        _userAppService.delete(Long.valueOf(id));
    }

    @RequestMapping(
        value = "/updateProfile",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UserProfile> updateProfile(@RequestBody @Valid UserProfile userProfile) {
        UserEntity user = _userAppService.getUser();

        FindUserWithAllFieldsByIdOutput currentUser = _userAppService.findWithAllFieldsById(user.getId());
        return new ResponseEntity(_userAppService.updateUserProfile(currentUser, userProfile), HttpStatus.OK);
    }

    // ------------ Update user ------------
    @PreAuthorize("hasAnyAuthority('USERENTITY_UPDATE')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UpdateUserOutput> update(@PathVariable String id, @RequestBody @Valid UpdateUserInput user) {
        FindUserWithAllFieldsByIdOutput currentUser = _userAppService.findWithAllFieldsById(Long.valueOf(id));
        Optional
            .ofNullable(currentUser)
            .orElseThrow(
                () -> new EntityNotFoundException(String.format("Unable to update. User with id=%s not found.", id))
            );

        user.setPassword(pEncoder.encode(currentUser.getPassword()));
        user.setIsEmailConfirmed(currentUser.getIsEmailConfirmed());
        if (currentUser.getIsActive() && !user.getIsActive()) {
            _jwtAppService.deleteAllUserTokens(currentUser.getUserName());
        }

        user.setVersiono(currentUser.getVersiono());
        UpdateUserOutput output = _userAppService.update(Long.valueOf(id), user);
        return new ResponseEntity(output, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/getProfile",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<UserProfile> getProfile() {
        UserEntity user = _userAppService.getUser();

        FindUserByIdOutput currentUser = _userAppService.findById(user.getId());
        return new ResponseEntity(_userAppService.getProfile(currentUser), HttpStatus.OK);
    }

    @RequestMapping(
        value = "/updateTheme",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<HashMap<String, String>> updateTheme(@RequestParam @Valid String theme) {
        UserEntity user = _userAppService.getUser();
        _userAppService.updateTheme(user, theme);

        String msg = "Theme updated successfully !";
        HashMap resultMap = new HashMap<String, String>();
        resultMap.put("message", msg);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @RequestMapping(
        value = "/updateLanguage",
        method = RequestMethod.PUT,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<HashMap<String, String>> updateLanguage(@RequestParam @Valid String language) {
        UserEntity user = _userAppService.getUser();
        _userAppService.updateLanguage(user, language);

        String msg = "Language updated successfully !";
        HashMap resultMap = new HashMap<String, String>();
        resultMap.put("message", msg);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USERENTITY_READ')")
    @RequestMapping(
        value = "/{id}",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity<FindUserByIdOutput> findById(@PathVariable String id) {
        FindUserByIdOutput output = _userAppService.findById(Long.valueOf(id));
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USERENTITY_READ')")
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

        return ResponseEntity.ok(_userAppService.find(searchCriteria, Pageable));
    }

    @PreAuthorize("hasAnyAuthority('USERENTITY_READ')")
    @RequestMapping(
        value = "/{id}/userpermissions",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity getUserpermissions(
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

        Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
        Map<String, String> joinColDetails = _userAppService.parseUserpermissionsJoinColumn(id);
        Optional
            .ofNullable(joinColDetails)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid join column")));

        searchCriteria.setJoinColumns(joinColDetails);

        List<FindUserpermissionByIdOutput> output = _userpermissionAppService.find(searchCriteria, pageable);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USERENTITY_READ')")
    @RequestMapping(
        value = "/{id}/userroles",
        method = RequestMethod.GET,
        consumes = { "application/json" },
        produces = { "application/json" }
    )
    public ResponseEntity getUserroles(
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

        Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

        SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
        Map<String, String> joinColDetails = _userAppService.parseUserrolesJoinColumn(id);
        Optional
            .ofNullable(joinColDetails)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Invalid join column")));

        searchCriteria.setJoinColumns(joinColDetails);

        List<FindUserroleByIdOutput> output = _userroleAppService.find(searchCriteria, pageable);
        Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

        return new ResponseEntity(output, HttpStatus.OK);
    }
}
