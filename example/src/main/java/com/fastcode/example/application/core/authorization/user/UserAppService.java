package com.fastcode.example.application.core.authorization.user;

import com.fastcode.example.addons.reporting.domain.dashboardversion.*;
import com.fastcode.example.addons.reporting.domain.dashboardversionreport.*;
import com.fastcode.example.addons.reporting.domain.reportversion.*;
import com.fastcode.example.application.core.authorization.user.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.domain.core.authorization.user.QUserEntity;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import com.fastcode.example.domain.core.authorization.userpreference.IUserpreferenceRepository;
import com.fastcode.example.domain.core.authorization.userpreference.UserpreferenceEntity;
import com.fastcode.example.security.SecurityUtils;
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

@Service("userAppService")
@RequiredArgsConstructor
public class UserAppService implements IUserAppService {

    @Qualifier("dashboardversionRepository")
    @NonNull
    protected final IDashboardversionRepository _dashboardversionRepository;

    @Qualifier("reportversionRepository")
    @NonNull
    protected final IReportversionRepository _reportversionRepository;

    @Qualifier("dashboardversionreportRepository")
    @NonNull
    protected final IDashboardversionreportRepository _reportDashboardRepository;

    public static final long PASSWORD_TOKEN_EXPIRATION_TIME = 3_600_000; // 1 hour

    @Qualifier("userRepository")
    @NonNull
    protected final IUserRepository _userRepository;

    @Qualifier("userpreferenceRepository")
    @NonNull
    protected final IUserpreferenceRepository _userpreferenceRepository;

    @Qualifier("IUserMapperImpl")
    @NonNull
    protected final IUserMapper mapper;

    @NonNull
    protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
    public CreateUserOutput create(CreateUserInput input) {
        UserEntity user = mapper.createUserInputToUserEntity(input);

        UserEntity createdUser = _userRepository.save(user);
        UserpreferenceEntity userPreference = createDefaultUserPreference(createdUser);
        return mapper.userEntityToCreateUserOutput(createdUser, userPreference);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UpdateUserOutput update(Long userId, UpdateUserInput input) {
        UserEntity user = mapper.updateUserInputToUserEntity(input);

        UserEntity updatedUser = _userRepository.save(user);
        return mapper.userEntityToUpdateUserOutput(updatedUser);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long userId) {
        UserEntity existing = _userRepository.findById(userId).orElse(null);

        List<DashboardversionreportEntity> dvrList = _reportDashboardRepository.findByUserId(userId);
        for (DashboardversionreportEntity dvr : dvrList) {
            _reportDashboardRepository.delete(dvr);
        }

        List<DashboardversionEntity> dvList = _dashboardversionRepository.findByUserId(userId);
        for (DashboardversionEntity du : dvList) {
            _dashboardversionRepository.delete(du);
        }

        List<ReportversionEntity> rvList = _reportversionRepository.findByUserId(userId);
        for (ReportversionEntity rv : rvList) {
            _reportversionRepository.delete(rv);
        }

        UserpreferenceEntity userpreference = _userpreferenceRepository.findById(userId).orElse(null);
        _userpreferenceRepository.delete(userpreference);
        _userRepository.delete(existing);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindUserByIdOutput findById(Long userId) {
        UserEntity foundUser = _userRepository.findById(userId).orElse(null);
        if (foundUser == null) return null;

        UserpreferenceEntity userPreference = _userpreferenceRepository.findById(userId).orElse(null);

        return mapper.userEntityToFindUserByIdOutput(foundUser, userPreference);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserpreferenceEntity createDefaultUserPreference(UserEntity user) {
        UserpreferenceEntity userpreference = new UserpreferenceEntity();
        userpreference.setTheme("default-theme");
        userpreference.setLanguage("en");
        userpreference.setId(user.getId());
        userpreference.setUser(user);

        return _userpreferenceRepository.save(userpreference);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateTheme(UserEntity user, String theme) {
        UserpreferenceEntity userpreference = _userpreferenceRepository.findById(user.getId()).orElse(null);
        userpreference.setTheme(theme);

        _userpreferenceRepository.save(userpreference);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateLanguage(UserEntity user, String language) {
        UserpreferenceEntity userpreference = _userpreferenceRepository.findById(user.getId()).orElse(null);
        userpreference.setLanguage(language);

        _userpreferenceRepository.save(userpreference);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindUserWithAllFieldsByIdOutput findWithAllFieldsById(Long userId) {
        UserEntity foundUser = _userRepository.findById(userId).orElse(null);
        if (foundUser == null) return null;

        return mapper.userEntityToFindUserWithAllFieldsByIdOutput(foundUser);
    }

    public UserProfile getProfile(FindUserByIdOutput user) {
        return mapper.findUserByIdOutputToUserProfile(user);
    }

    public UserProfile updateUserProfile(FindUserWithAllFieldsByIdOutput user, UserProfile userProfile) {
        UpdateUserInput userInput = mapper.findUserWithAllFieldsByIdOutputAndUserProfileToUpdateUserInput(
            user,
            userProfile
        );
        UpdateUserOutput output = update(user.getId(), userInput);

        return mapper.updateUserOutputToUserProfile(output);
    }

    @Transactional(readOnly = true)
    public UserEntity getUser() {
        return _userRepository.findByUserName(SecurityUtils.getCurrentUserLogin().orElse(null));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindUserByNameOutput findByUserName(String userName) {
        UserEntity foundUser = _userRepository.findByUserName(userName);
        if (foundUser == null) {
            return null;
        }

        return mapper.userEntityToFindUserByNameOutput(foundUser);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public FindUserByNameOutput findByEmailAddress(String emailAddress) {
        UserEntity foundUser = _userRepository.findByEmailAddress(emailAddress);
        if (foundUser == null) {
            return null;
        }

        return mapper.userEntityToFindUserByNameOutput(foundUser);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserData(FindUserWithAllFieldsByIdOutput user) {
        UserEntity foundUser = mapper.findUserWithAllFieldsByIdOutputToUserEntity(user);
        _userRepository.save(foundUser);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<FindUserByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception {
        Page<UserEntity> foundUser = _userRepository.findAll(search(search), pageable);
        List<UserEntity> userList = foundUser.getContent();
        Iterator<UserEntity> userIterator = userList.iterator();
        List<FindUserByIdOutput> output = new ArrayList<>();

        while (userIterator.hasNext()) {
            UserEntity user = userIterator.next();
            UserpreferenceEntity userPreference = _userpreferenceRepository.findById(user.getId()).orElse(null);
            output.add(mapper.userEntityToFindUserByIdOutput(user, userPreference));
        }
        return output;
    }

    protected BooleanBuilder search(SearchCriteria search) throws Exception {
        QUserEntity user = QUserEntity.userEntity;
        if (search != null) {
            Map<String, SearchFields> map = new HashMap<>();
            for (SearchFields fieldDetails : search.getFields()) {
                map.put(fieldDetails.getFieldName(), fieldDetails);
            }
            List<String> keysList = new ArrayList<String>(map.keySet());
            checkProperties(keysList);
            return searchKeyValuePair(user, map, search.getJoinColumns());
        }
        return null;
    }

    protected void checkProperties(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    list.get(i).replace("%20", "").trim().equals("emailAddress") ||
                    list.get(i).replace("%20", "").trim().equals("firstName") ||
                    list.get(i).replace("%20", "").trim().equals("id") ||
                    list.get(i).replace("%20", "").trim().equals("isActive") ||
                    list.get(i).replace("%20", "").trim().equals("isEmailConfirmed") ||
                    list.get(i).replace("%20", "").trim().equals("lastName") ||
                    list.get(i).replace("%20", "").trim().equals("password") ||
                    list.get(i).replace("%20", "").trim().equals("phoneNumber") ||
                    list.get(i).replace("%20", "").trim().equals("userName")
                )
            ) {
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchKeyValuePair(
        QUserEntity user,
        Map<String, SearchFields> map,
        Map<String, String> joinColumns
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("emailAddress")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    user.emailAddress.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    user.emailAddress.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    user.emailAddress.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("firstName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    user.firstName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    user.firstName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    user.firstName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("id")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(user.id.eq(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    StringUtils.isNumeric(details.getValue().getSearchValue())
                ) builder.and(user.id.ne(Long.valueOf(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("range")
                ) {
                    if (
                        StringUtils.isNumeric(details.getValue().getStartingValue()) &&
                        StringUtils.isNumeric(details.getValue().getEndingValue())
                    ) builder.and(
                        user.id.between(
                            Long.valueOf(details.getValue().getStartingValue()),
                            Long.valueOf(details.getValue().getEndingValue())
                        )
                    ); else if (StringUtils.isNumeric(details.getValue().getStartingValue())) builder.and(
                        user.id.goe(Long.valueOf(details.getValue().getStartingValue()))
                    ); else if (StringUtils.isNumeric(details.getValue().getEndingValue())) builder.and(
                        user.id.loe(Long.valueOf(details.getValue().getEndingValue()))
                    );
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("isActive")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(user.isActive.eq(Boolean.parseBoolean(details.getValue().getSearchValue()))); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(user.isActive.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
            }
            if (details.getKey().replace("%20", "").trim().equals("isEmailConfirmed")) {
                if (
                    details.getValue().getOperator().equals("equals") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(
                    user.isEmailConfirmed.eq(Boolean.parseBoolean(details.getValue().getSearchValue()))
                ); else if (
                    details.getValue().getOperator().equals("notEqual") &&
                    (
                        details.getValue().getSearchValue().equalsIgnoreCase("true") ||
                        details.getValue().getSearchValue().equalsIgnoreCase("false")
                    )
                ) builder.and(user.isEmailConfirmed.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
            }
            if (details.getKey().replace("%20", "").trim().equals("lastName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    user.lastName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    user.lastName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    user.lastName.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("password")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    user.password.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    user.password.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    user.password.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("phoneNumber")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    user.phoneNumber.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    user.phoneNumber.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    user.phoneNumber.ne(details.getValue().getSearchValue())
                );
            }
            if (details.getKey().replace("%20", "").trim().equals("userName")) {
                if (details.getValue().getOperator().equals("contains")) builder.and(
                    user.userName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                ); else if (details.getValue().getOperator().equals("equals")) builder.and(
                    user.userName.eq(details.getValue().getSearchValue())
                ); else if (details.getValue().getOperator().equals("notEqual")) builder.and(
                    user.userName.ne(details.getValue().getSearchValue())
                );
            }
        }

        return builder;
    }

    public Map<String, String> parseDashboardsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("userId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseDashboardversionsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("userId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseReportsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("userId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseReportversionsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("userId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseUserpermissionsJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("userId", keysString);

        return joinColumnMap;
    }

    public Map<String, String> parseUserrolesJoinColumn(String keysString) {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        joinColumnMap.put("userId", keysString);

        return joinColumnMap;
    }
}
