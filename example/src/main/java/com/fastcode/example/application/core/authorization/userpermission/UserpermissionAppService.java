package com.fastcode.example.application.core.authorization.userpermission;

import com.fastcode.example.application.core.authorization.userpermission.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.authorization.permission.IPermissionRepository;
import com.fastcode.example.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import com.fastcode.example.domain.core.authorization.userpermission.IUserpermissionRepository;
import com.fastcode.example.domain.core.authorization.userpermission.QUserpermissionEntity;
import com.fastcode.example.domain.core.authorization.userpermission.UserpermissionEntity;
import com.fastcode.example.domain.core.authorization.userpermission.UserpermissionId;
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

@Service("userpermissionAppService")
@RequiredArgsConstructor
public class UserpermissionAppService implements IUserpermissionAppService {

    @Qualifier("userpermissionRepository")
    @NonNull
    protected final IUserpermissionRepository _userpermissionRepository;

    @Qualifier("permissionRepository")
    @NonNull
    protected final IPermissionRepository _permissionRepository;

    @Qualifier("userRepository")
    @NonNull
    protected final IUserRepository _userRepository;

    @Qualifier("IUserpermissionMapperImpl")
    @NonNull
    protected final IUserpermissionMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateUserpermissionOutput create(CreateUserpermissionInput input) {
        UserpermissionEntity userpermission = mapper.createUserpermissionInputToUserpermissionEntity(input);
        PermissionEntity foundPermission = null;
        UserEntity foundUser = null;
        if (input.getPermissionId() != null) {
            foundPermission = _permissionRepository.findById(input.getPermissionId()).orElse(null);
        } else {
            return null;
        }
        if (input.getUserId() != null) {
            foundUser = _userRepository.findById(input.getUserId()).orElse(null);
        } else {
            return null;
        }
        if (foundUser != null || foundPermission != null) {
            if (!checkIfPermissionAlreadyAssigned(foundUser, foundPermission)) {
                userpermission.setPermission(foundPermission);
                userpermission.setUser(foundUser);
            }
        } else {
            return null;
        }

        UserpermissionEntity createdUserpermission = _userpermissionRepository.save(userpermission);
        CreateUserpermissionOutput output = mapper.userpermissionEntityToCreateUserpermissionOutput(
            createdUserpermission
        );

        output.setRevoked(input.getRevoked());
        return output;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateUserpermissionOutput update(UserpermissionId userpermissionId, UpdateUserpermissionInput input) {
        UserpermissionEntity userpermission = mapper.updateUserpermissionInputToUserpermissionEntity(input);
        PermissionEntity foundPermission = null;
        UserEntity foundUser = null;

        if (input.getPermissionId() != null) {
            foundPermission = _permissionRepository.findById(input.getPermissionId()).orElse(null);
        } else {
            return null;
        }

        if (input.getUserId() != null) {
            foundUser = _userRepository.findById(input.getUserId()).orElse(null);
        } else {
            return null;
        }
        if (foundUser != null || foundPermission != null) {
            if (checkIfPermissionAlreadyAssigned(foundUser, foundPermission)) {
                userpermission.setPermission(foundPermission);
                userpermission.setUser(foundUser);
                userpermission.setRevoked(input.getRevoked());
            }
        } else {
            return null;
        }
        UserpermissionEntity updatedUserpermission = _userpermissionRepository.save(userpermission);
        return mapper.userpermissionEntityToUpdateUserpermissionOutput(updatedUserpermission);
    }

    public boolean checkIfPermissionAlreadyAssigned(UserEntity foundUser, PermissionEntity foundPermission) {
        List<UserpermissionEntity> userPermission = _userpermissionRepository.findByUserId(foundUser.getId());

        Iterator pIterator = userPermission.iterator();
        while (pIterator.hasNext()) {
            UserpermissionEntity pe = (UserpermissionEntity) pIterator.next();
            if (pe.getPermission() == foundPermission) {
                return true;
            }
        }

        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UserpermissionId userpermissionId) {
        UserpermissionEntity existing = _userpermissionRepository.findById(userpermissionId).orElse(null);
        _userpermissionRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindUserpermissionByIdOutput findById(UserpermissionId userpermissionId) {
        UserpermissionEntity foundUserpermission = _userpermissionRepository.findById(userpermissionId).orElse(null);
        if (foundUserpermission == null) return null;

        return mapper.userpermissionEntityToFindUserpermissionByIdOutput(foundUserpermission);
    }

    //Permission
    // ReST API Call - GET /userpermission/1/permission
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetPermissionOutput getPermission(UserpermissionId userpermissionId) {
        UserpermissionEntity foundUserpermission = _userpermissionRepository.findById(userpermissionId).orElse(null);
        if (foundUserpermission == null) {
            logHelper.getLogger().error("There does not exist a userpermission wth a id=%s", userpermissionId);
            return null;
        }
        PermissionEntity re = foundUserpermission.getPermission();
        return mapper.permissionEntityToGetPermissionOutput(re, foundUserpermission);
    }

    //User
    // ReST API Call - GET /userpermission/1/user
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public GetUserOutput getUser(UserpermissionId userpermissionId) {
        UserpermissionEntity foundUserpermission = _userpermissionRepository.findById(userpermissionId).orElse(null);
        if (foundUserpermission == null) {
            logHelper.getLogger().error("There does not exist a userpermission wth a id=%s", userpermissionId);
            return null;
        }
        UserEntity re = foundUserpermission.getUser();
        return mapper.userEntityToGetUserOutput(re, foundUserpermission);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindUserpermissionByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<UserpermissionEntity> foundUserpermission = _userpermissionRepository.findAll(search(search), pageable);
        List<UserpermissionEntity> userpermissionList = foundUserpermission.getContent();
        Iterator<UserpermissionEntity> userpermissionIterator = userpermissionList.iterator();
        List<FindUserpermissionByIdOutput> output = new ArrayList<>();

        while (userpermissionIterator.hasNext()) {
            UserpermissionEntity userpermission = userpermissionIterator.next();
            output.add(mapper.userpermissionEntityToFindUserpermissionByIdOutput(userpermission));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QUserpermissionEntity userpermission = QUserpermissionEntity.userpermissionEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(userpermission, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    //       list.get(i).replace("%20","").trim().equals("displayName") ||
                    //       list.get(i).replace("%20","").trim().equals("userName") ||
                    list.get(i).replace("%20", "").trim().equals("permissionId") ||
                    list.get(i).replace("%20", "").trim().equals("revoked") ||
                    list.get(i).replace("%20", "").trim().equals("userId")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QUserpermissionEntity userpermission,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("permissionId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(
                    userpermission.permissionId.eq(Long.valueOf(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(
                    userpermission.permissionId.ne(Long.valueOf(details.getValue().getSearchValue()))
                ); else if (details.getValue().getOperator().equals("range")) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        userpermission.permissionId.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        userpermission.permissionId.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        userpermission.permissionId.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("revoked")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(
                    userpermission.revoked.eq(Boolean.parseBoolean(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(userpermission.revoked.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
            }
            if (details.getKey().replace("%20", "").trim().equals("userId")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(userpermission.userId.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(userpermission.userId.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        userpermission.userId.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        userpermission.userId.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        userpermission.userId.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }

            if (details.getKey().replace("%20", "").trim().equals("permission")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    userpermission.permission.displayName.likeIgnoreCase(
                        "%" + details.getValue().getSearchValue() + "%"
                    )
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    userpermission.permission.displayName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    userpermission.permission.displayName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("user")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    userpermission.user.userName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    userpermission.user.userName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    userpermission.user.userName.ne(details.getValue().getSearchValue())
                );
            }
        }

        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("permissionId")) {
                builder.and(userpermission.permission.id.eq(Long.parseLong(joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("permission")) {
                builder.and(userpermission.permission.displayName.eq(joinCol.getValue()));
            }
        }
        for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
            if (joinCol != null && joinCol.getKey().equals("userId")) {
                builder.and(userpermission.user.id.eq(Long.parseLong(joinCol.getValue())));
            }

            if (joinCol != null && joinCol.getKey().equals("user")) {
                builder.and(userpermission.user.userName.eq(joinCol.getValue()));
            }
        }
        return builder;
    }

    public UserpermissionId parseUserpermissionKey(String keysString) {
        String[] keyEntries = keysString.split(",");
        UserpermissionId userpermissionId = new UserpermissionId();

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

        userpermissionId.setPermissionId(Long.valueOf(keyMap.get("permissionId")));
        userpermissionId.setUserId(Long.valueOf(keyMap.get("userId")));
        return userpermissionId;
    }
}
