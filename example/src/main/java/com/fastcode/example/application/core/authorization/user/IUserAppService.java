package com.fastcode.example.application.core.authorization.user;

import com.fastcode.example.application.core.authorization.user.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import com.fastcode.example.domain.core.authorization.userpreference.UserpreferenceEntity;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IUserAppService {
    //CRUD Operations

    CreateUserOutput create(CreateUserInput user);

    void delete(Long id);

    UpdateUserOutput update(Long id, UpdateUserInput input);

    FindUserByIdOutput findById(Long id);

    List<FindUserByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;

    UserpreferenceEntity createDefaultUserPreference(UserEntity user);

    void updateTheme(UserEntity user, String theme);

    void updateLanguage(UserEntity user, String language);

    void updateUserData(FindUserWithAllFieldsByIdOutput user);

    UserProfile updateUserProfile(FindUserWithAllFieldsByIdOutput user, UserProfile userProfile);

    FindUserWithAllFieldsByIdOutput findWithAllFieldsById(Long userId);

    UserProfile getProfile(FindUserByIdOutput user);

    UserEntity getUser();

    FindUserByNameOutput findByUserName(String userName);

    FindUserByNameOutput findByEmailAddress(String emailAddress);

    //Join Column Parsers

    Map<String, String> parseDashboardsJoinColumn(String keysString);

    Map<String, String> parseDashboardversionsJoinColumn(String keysString);

    Map<String, String> parseReportsJoinColumn(String keysString);

    Map<String, String> parseReportversionsJoinColumn(String keysString);

    Map<String, String> parseUserpermissionsJoinColumn(String keysString);

    Map<String, String> parseUserrolesJoinColumn(String keysString);
}
