package com.fastcode.example.application.core.authorization.userrole;

import com.fastcode.example.application.core.authorization.userrole.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.authorization.role.IRoleRepository;
import com.fastcode.example.domain.core.authorization.role.RoleEntity;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import com.fastcode.example.domain.core.authorization.userrole.IUserroleRepository;
import com.fastcode.example.domain.core.authorization.userrole.QUserroleEntity;
import com.fastcode.example.domain.core.authorization.userrole.UserroleEntity;
import com.fastcode.example.domain.core.authorization.userrole.UserroleId;
import com.querydsl.core.BooleanBuilder;
import java.time.*;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userroleAppService")
@RequiredArgsConstructor
public class UserroleAppService implements IUserroleAppService {

    @Qualifier("userroleRepository")
    @NonNull
    protected final IUserroleRepository _userroleRepository;

    @Qualifier("roleRepository")
    @NonNull
    protected final IRoleRepository _roleRepository;

    @Qualifier("userRepository")
    @NonNull
    protected final IUserRepository _userRepository;

    @Qualifier("IUserroleMapperImpl")
    @NonNull
    protected final IUserroleMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateUserroleOutput create(CreateUserroleInput input) {
        UserroleEntity userrole = mapper.createUserroleInputToUserroleEntity(input);
        RoleEntity foundRole = null;
        UserEntity foundUser = null;
        if (input.getRoleId() != null) {
            foundRole = _roleRepository.findById(input.getRoleId()).orElse(null);
        } else {
            return null;
        }
        if (input.getUserId() != null) {
            foundUser = _userRepository.findById(input.getUserId()).orElse(null);
        } else {
            return null;
        }
        if (foundUser != null || foundRole != null) {
            if (!checkIfRoleAlreadyAssigned(foundUser, foundRole)) {
                userrole.setRole(foundRole);
                userrole.setUser(foundUser);
            }
        } else {
            return null;
        }

        UserroleEntity createdUserrole = _userroleRepository.save(userrole);
        return mapper.userroleEntityToCreateUserroleOutput(createdUserrole);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateUserroleOutput update(UserroleId userroleId, UpdateUserroleInput input) {
        UserroleEntity userrole = mapper.updateUserroleInputToUserroleEntity(input);
        RoleEntity foundRole = null;
        UserEntity foundUser = null;

        if (input.getRoleId() != null) {
            foundRole = _roleRepository.findById(input.getRoleId()).orElse(null);
        } else {
            return null;
        }

        if (input.getUserId() != null) {
            foundUser = _userRepository.findById(input.getUserId()).orElse(null);
        } else {
            return null;
        }
        if (foundUser != null || foundRole != null) {
            if (checkIfRoleAlreadyAssigned(foundUser, foundRole)) {
                userrole.setRole(foundRole);
                userrole.setUser(foundUser);
            }
        } else {
            return null;
        }

        UserroleEntity updatedUserrole = _userroleRepository.save(userrole);
        return mapper.userroleEntityToUpdateUserroleOutput(updatedUserrole);
    }

    public boolean checkIfRoleAlreadyAssigned(UserEntity foundUser, RoleEntity foundRole) {
        List<UserroleEntity> userRole = _userroleRepository.findByUserId(foundUser.getId());

        Iterator rIterator = userRole.iterator();
        while (rIterator.hasNext()) {
            UserroleEntity ur = (UserroleEntity) rIterator.next();
            if (ur.getRole() == foundRole) {
                return true;
            }
        }

        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UserroleId userroleId) {
        UserroleEntity existing = _userroleRepository.findById(userroleId).orElse(null);
        _userroleRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindUserroleByIdOutput findById(UserroleId userroleId) {
        UserroleEntity foundUserrole = _userroleRepository.findById(userroleId).orElse(null);
        if (foundUserrole == null) return null;

        return mapper.userroleEntityToFindUserroleByIdOutput(foundUserrole);
    }

    //Role
    // ReST API Call - GET /userrole/1/role
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetRoleOutput getRole(UserroleId userroleId) {
        UserroleEntity foundUserrole = _userroleRepository.findById(userroleId).orElse(null);
        if (foundUserrole == null) {
            logHelper.getLogger().error("There does not exist a userrole wth a id=%s", userroleId);
            return null;
        }
        RoleEntity re = foundUserrole.getRole();
        return mapper.roleEntityToGetRoleOutput(re, foundUserrole);
    }

    //User
    // ReST API Call - GET /userrole/1/user
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetUserOutput getUser(UserroleId userroleId) {
        UserroleEntity foundUserrole = _userroleRepository.findById(userroleId).orElse(null);
        if (foundUserrole == null) {
            logHelper.getLogger().error("There does not exist a userrole wth a id=%s", userroleId);
            return null;
        }
        UserEntity re = foundUserrole.getUser();
        return mapper.userEntityToGetUserOutput(re, foundUserrole);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindUserroleByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<UserroleEntity> foundUserrole = _userroleRepository.findAll(search(search), pageable);
        List<UserroleEntity> userroleList = foundUserrole.getContent();
        Iterator<UserroleEntity> userroleIterator = userroleList.iterator();
        List<FindUserroleByIdOutput> output = new ArrayList<>();

        while (userroleIterator.hasNext()) {
            UserroleEntity userrole = userroleIterator.next();
            output.add(mapper.userroleEntityToFindUserroleByIdOutput(userrole));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QUserroleEntity userrole = QUserroleEntity.userroleEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(userrole, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    //       list.get(i).replace("%20","").trim().equals("displayName") ||
                    //       list.get(i).replace("%20","").trim().equals("userName") ||
                    list.get(i).replace("%20", "").trim().equals("roleId") ||
                    list.get(i).replace("%20", "").trim().equals("userId")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QUserroleEntity userrole,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("roleId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(userrole.roleId.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(userrole.roleId.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        userrole.roleId.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        userrole.roleId.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        userrole.roleId.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("userId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(userrole.userId.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(userrole.userId.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        userrole.userId.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        userrole.userId.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        userrole.userId.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }

            if (details.getKey().replace("%20", "").trim().equals("role")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    userrole.role.displayName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    userrole.role.displayName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    userrole.role.displayName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("user")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    userrole.user.userName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    userrole.user.userName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    userrole.user.userName.ne(details.getValue().getSearchValue())
                );
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("roleId")) {
                builder.and(userrole.role.id.eq(Long.parseLong(joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("role")) {
                builder.and(userrole.role.displayName.eq(joinCol.getValue()));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("userId")) {
                builder.and(userrole.user.id.eq(Long.parseLong(joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("user")) {
                builder.and(userrole.user.userName.eq(joinCol.getValue()));
            }
        }
        return builder;
    }

    public UserroleId parseUserroleKey(String keysString) {
        String[] keyEntries = keysString.split(",");
        UserroleId userroleId = new UserroleId();

        Map<String, String> keyMap = new HashMap<String, String>();
        if (keyEntries.length > 1) {
            for (String keyEntry : keyEntries) {
                String[] keyEntryArr = keyEntry.split("=");
                if (keyEntryArr.length > 1) {
                    keyMap.put(keyEntryArr[0], keyEntryArr[1]);
                } else {
                    return null;
                }
            }
        } else {
            String[] keyEntryArr = keysString.split("=");
            if (keyEntryArr.length > 1) {
                keyMap.put(keyEntryArr[0], keyEntryArr[1]);
            } else return null;
        }

        userroleId.setRoleId(Long.valueOf(keyMap.get("roleId")));
        userroleId.setUserId(Long.valueOf(keyMap.get("userId")));
        return userroleId;
    }
}
