package com.fastcode.example.application.core.authorization.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.example.addons.reporting.domain.dashboardversion.*;
import com.fastcode.example.addons.reporting.domain.dashboardversionreport.*;
import com.fastcode.example.addons.reporting.domain.reportversion.*;
import com.fastcode.example.application.core.authorization.user.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.authorization.role.IRoleRepository;
import com.fastcode.example.domain.core.authorization.user.*;
import com.fastcode.example.domain.core.authorization.user.QUserEntity;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import com.fastcode.example.domain.core.authorization.userpreference.IUserpreferenceRepository;
import com.fastcode.example.domain.core.authorization.userpreference.UserpreferenceEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserAppServiceTest {

    @InjectMocks
    @Spy
    protected UserAppService _appService;

    @Mock
    protected IUserRepository _userRepository;

    @Mock
    protected IDashboardversionRepository _dashboardversionRepository;

    @Mock
    protected IReportversionRepository _reportversionRepository;

    @Mock
    protected IDashboardversionreportRepository _reportDashboardRepository;

    @Mock
    protected IUserpreferenceRepository _userpreferenceRepository;

    @Mock
    protected IUserMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected IRoleRepository _roleRepository;

    protected static Long ID = 15L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findUserById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<UserEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_userRepository.findById(anyLong())).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findUserById_IdIsNotNullAndIdExists_ReturnUser() {
        UserEntity user = mock(UserEntity.class);
        Optional<UserEntity> userOptional = Optional.of((UserEntity) user);
        UserpreferenceEntity userpreference = new UserpreferenceEntity();
        Optional<UserpreferenceEntity> userpreferenceOptional = Optional.of((UserpreferenceEntity) userpreference);
        Mockito.when(_userpreferenceRepository.findById(ID)).thenReturn(userpreferenceOptional);
        Mockito.when(_userRepository.findById(anyLong())).thenReturn(userOptional);

        Assertions
            .assertThat(_appService.findById(ID))
            .isEqualTo(_mapper.userEntityToFindUserByIdOutput(user, userpreference));
    }

    @Test
    public void createUser_UserIsNotNullAndUserDoesNotExist_StoreUser() {
        UserEntity userEntity = mock(UserEntity.class);
        CreateUserInput userInput = new CreateUserInput();

        UserpreferenceEntity userpreference = new UserpreferenceEntity();
        Optional<UserpreferenceEntity> userpreferenceOptional = Optional.of((UserpreferenceEntity) userpreference);
        Mockito.when(_userpreferenceRepository.findById(ID)).thenReturn(userpreferenceOptional);

        Mockito.when(_mapper.createUserInputToUserEntity(any(CreateUserInput.class))).thenReturn(userEntity);
        Mockito.when(_userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        Assertions
            .assertThat(_appService.create(userInput))
            .isEqualTo(_mapper.userEntityToCreateUserOutput(userEntity, userpreference));
    }

    @Test
    public void updateUser_UserIdIsNotNullAndIdExists_ReturnUpdatedUser() {
        UserEntity userEntity = mock(UserEntity.class);
        UpdateUserInput user = mock(UpdateUserInput.class);

        Mockito.when(_mapper.updateUserInputToUserEntity(any(UpdateUserInput.class))).thenReturn(userEntity);
        Mockito.when(_userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        Assertions.assertThat(_appService.update(ID, user)).isEqualTo(_mapper.userEntityToUpdateUserOutput(userEntity));
    }

    @Test
    public void deleteUser_UserIsNotNullAndUserExists_UserRemoved() {
        UserEntity user = mock(UserEntity.class);
        Optional<UserEntity> userOptional = Optional.of((UserEntity) user);
        Mockito.when(_userRepository.findById(anyLong())).thenReturn(userOptional);

        List<DashboardversionreportEntity> dvrList = new ArrayList<>();
        Mockito.when(_reportDashboardRepository.findByUserId(anyLong())).thenReturn(dvrList);

        List<DashboardversionEntity> dvList = new ArrayList<>();
        Mockito.when(_dashboardversionRepository.findByUserId(anyLong())).thenReturn(dvList);

        List<ReportversionEntity> rvList = new ArrayList<>();
        Mockito.when(_reportversionRepository.findByUserId(anyLong())).thenReturn(rvList);
        UserpreferenceEntity userpreference = mock(UserpreferenceEntity.class);
        Optional<UserpreferenceEntity> userpreferenceOptional = Optional.of((UserpreferenceEntity) userpreference);
        Mockito.when(_userRepository.findById(anyLong())).thenReturn(userOptional);
        Mockito.when(_userpreferenceRepository.findById(anyLong())).thenReturn(userpreferenceOptional);
        doNothing().when(_userpreferenceRepository).delete(any(UserpreferenceEntity.class));

        _appService.delete(ID);
        verify(_userRepository).delete(user);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<UserEntity> list = new ArrayList<>();
        Page<UserEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindUserByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_userRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<UserEntity> list = new ArrayList<>();
        UserEntity user = mock(UserEntity.class);
        list.add(user);
        Page<UserEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindUserByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        UserpreferenceEntity userpreference = new UserpreferenceEntity();
        Optional<UserpreferenceEntity> userpreferenceOptional = Optional.of((UserpreferenceEntity) userpreference);
        Mockito.when(_userpreferenceRepository.findById(any(Long.class))).thenReturn(userpreferenceOptional);

        output.add(_mapper.userEntityToFindUserByIdOutput(user, userpreference));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_userRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QUserEntity user = QUserEntity.userEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("emailAddress", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.emailAddress.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(user, map, searchMap)).isEqualTo(builder);
    }

    @Test(expected = Exception.class)
    public void checkProperties_PropertyDoesNotExist_ThrowException() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("xyz");
        _appService.checkProperties(list);
    }

    @Test
    public void checkProperties_PropertyExists_ReturnNothing() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("emailAddress");
        list.add("firstName");
        list.add("lastName");
        list.add("password");
        list.add("phoneNumber");
        list.add("userName");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QUserEntity user = QUserEntity.userEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setFieldName("emailAddress");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(user.emailAddress.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QUserEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    @Test
    public void findUserByName_NameIsNotNullAndUserDoesNotExist_ReturnNull() {
        Mockito.when(_userRepository.findByUserName(anyString())).thenReturn(null);
        Assertions.assertThat(_appService.findByUserName("User1")).isEqualTo(null);
    }

    @Test
    public void findUserByName_NameIsNotNullAndUserExists_ReturnAUser() {
        UserEntity user = mock(UserEntity.class);
        Mockito.when(_userRepository.findByUserName(anyString())).thenReturn(user);
        Assertions
            .assertThat(_appService.findByUserName("User1"))
            .isEqualTo(_mapper.userEntityToFindUserByNameOutput(user));
    }

    @Test
    public void ParsedashboardsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("userId", keyString);
        Assertions.assertThat(_appService.parseDashboardsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }

    @Test
    public void ParsedashboardversionsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("userId", keyString);
        Assertions.assertThat(_appService.parseDashboardversionsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }

    @Test
    public void ParsereportsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("userId", keyString);
        Assertions.assertThat(_appService.parseReportsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }

    @Test
    public void ParsereportversionsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("userId", keyString);
        Assertions.assertThat(_appService.parseReportversionsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }

    @Test
    public void ParseuserpermissionsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("userId", keyString);
        Assertions.assertThat(_appService.parseUserpermissionsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }

    @Test
    public void ParseuserrolesJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("userId", keyString);
        Assertions.assertThat(_appService.parseUserrolesJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
