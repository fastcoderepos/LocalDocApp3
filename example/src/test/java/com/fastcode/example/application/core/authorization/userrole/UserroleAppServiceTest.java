package com.fastcode.example.application.core.authorization.userrole;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.example.application.core.authorization.userrole.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.authorization.role.IRoleRepository;
import com.fastcode.example.domain.core.authorization.role.RoleEntity;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import com.fastcode.example.domain.core.authorization.userrole.*;
import com.fastcode.example.domain.core.authorization.userrole.QUserroleEntity;
import com.fastcode.example.domain.core.authorization.userrole.UserroleEntity;
import com.fastcode.example.domain.core.authorization.userrole.UserroleId;
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
public class UserroleAppServiceTest {

    @InjectMocks
    @Spy
    protected UserroleAppService _appService;

    @Mock
    protected IUserroleRepository _userroleRepository;

    @Mock
    protected IRoleRepository _roleRepository;

    @Mock
    protected IUserRepository _userRepository;

    @Mock
    protected IUserroleMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected UserroleId userroleId;

    private static final Long ID = 15L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findUserroleById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<UserroleEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(userroleId)).isEqualTo(null);
    }

    @Test
    public void findUserroleById_IdIsNotNullAndIdExists_ReturnUserrole() {
        UserroleEntity userrole = mock(UserroleEntity.class);
        Optional<UserroleEntity> userroleOptional = Optional.of((UserroleEntity) userrole);
        Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(userroleOptional);

        Assertions
            .assertThat(_appService.findById(userroleId))
            .isEqualTo(_mapper.userroleEntityToFindUserroleByIdOutput(userrole));
    }

    @Test
    public void createUserrole_UserroleIsNotNullAndUserroleDoesNotExist_StoreUserrole() {
        UserroleEntity userroleEntity = mock(UserroleEntity.class);
        CreateUserroleInput userroleInput = new CreateUserroleInput();

        RoleEntity role = mock(RoleEntity.class);
        Optional<RoleEntity> roleOptional = Optional.of((RoleEntity) role);
        userroleInput.setRoleId(15L);

        Mockito.when(_roleRepository.findById(any(Long.class))).thenReturn(roleOptional);

        UserEntity user = mock(UserEntity.class);
        Optional<UserEntity> userOptional = Optional.of((UserEntity) user);
        userroleInput.setUserId(15L);

        Mockito.when(_userRepository.findById(any(Long.class))).thenReturn(userOptional);

        Mockito
            .when(_mapper.createUserroleInputToUserroleEntity(any(CreateUserroleInput.class)))
            .thenReturn(userroleEntity);
        Mockito.when(_userroleRepository.save(any(UserroleEntity.class))).thenReturn(userroleEntity);

        Assertions
            .assertThat(_appService.create(userroleInput))
            .isEqualTo(_mapper.userroleEntityToCreateUserroleOutput(userroleEntity));
    }

    @Test
    public void createUserrole_UserroleIsNotNullAndUserroleDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        CreateUserroleInput userrole = mock(CreateUserroleInput.class);

        Mockito.when(_mapper.createUserroleInputToUserroleEntity(any(CreateUserroleInput.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(userrole)).isEqualTo(null);
    }

    @Test
    public void createUserrole_UserroleIsNotNullAndUserroleDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        CreateUserroleInput userrole = new CreateUserroleInput();

        userrole.setRoleId(15L);

        Optional<RoleEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_roleRepository.findById(any(Long.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.create(userrole)).isEqualTo(null);
    }

    @Test
    public void updateUserrole_UserroleIsNotNullAndUserroleDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        UpdateUserroleInput userrole = mock(UpdateUserroleInput.class);
        UserroleEntity userroleEntity = mock(UserroleEntity.class);

        Mockito
            .when(_mapper.updateUserroleInputToUserroleEntity(any(UpdateUserroleInput.class)))
            .thenReturn(userroleEntity);
        Assertions.assertThat(_appService.update(userroleId, userrole)).isEqualTo(null);
    }

    @Test
    public void updateUserrole_UserroleIsNotNullAndUserroleDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        UpdateUserroleInput userrole = new UpdateUserroleInput();
        userrole.setRoleId(15L);

        Optional<RoleEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_roleRepository.findById(any(Long.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.update(userroleId, userrole)).isEqualTo(null);
    }

    @Test
    public void updateUserrole_UserroleIdIsNotNullAndIdExists_ReturnUpdatedUserrole() {
        UserroleEntity userroleEntity = mock(UserroleEntity.class);
        UpdateUserroleInput userrole = mock(UpdateUserroleInput.class);

        Mockito
            .when(_mapper.updateUserroleInputToUserroleEntity(any(UpdateUserroleInput.class)))
            .thenReturn(userroleEntity);
        Mockito.when(_userroleRepository.save(any(UserroleEntity.class))).thenReturn(userroleEntity);
        Assertions
            .assertThat(_appService.update(userroleId, userrole))
            .isEqualTo(_mapper.userroleEntityToUpdateUserroleOutput(userroleEntity));
    }

    @Test
    public void deleteUserrole_UserroleIsNotNullAndUserroleExists_UserroleRemoved() {
        UserroleEntity userrole = mock(UserroleEntity.class);
        Optional<UserroleEntity> userroleOptional = Optional.of((UserroleEntity) userrole);
        Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(userroleOptional);

        _appService.delete(userroleId);
        verify(_userroleRepository).delete(userrole);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<UserroleEntity> list = new ArrayList<>();
        Page<UserroleEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindUserroleByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_userroleRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<UserroleEntity> list = new ArrayList<>();
        UserroleEntity userrole = mock(UserroleEntity.class);
        list.add(userrole);
        Page<UserroleEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindUserroleByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.userroleEntityToFindUserroleByIdOutput(userrole));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_userroleRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QUserroleEntity userrole = QUserroleEntity.userroleEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        Assertions.assertThat(_appService.searchKeyValuePair(userrole, map, searchMap)).isEqualTo(builder);
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
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QUserroleEntity userrole = QUserroleEntity.userroleEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QUserroleEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Role
    @Test
    public void GetRole_IfUserroleIdAndRoleIdIsNotNullAndUserroleExists_ReturnRole() {
        UserroleEntity userrole = mock(UserroleEntity.class);
        Optional<UserroleEntity> userroleOptional = Optional.of((UserroleEntity) userrole);
        RoleEntity roleEntity = mock(RoleEntity.class);

        Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(userroleOptional);
        Mockito.when(userrole.getRole()).thenReturn(roleEntity);
        Assertions
            .assertThat(_appService.getRole(userroleId))
            .isEqualTo(_mapper.roleEntityToGetRoleOutput(roleEntity, userrole));
    }

    @Test
    public void GetRole_IfUserroleIdAndRoleIdIsNotNullAndUserroleDoesNotExist_ReturnNull() {
        Optional<UserroleEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getRole(userroleId)).isEqualTo(null);
    }

    //User
    @Test
    public void GetUser_IfUserroleIdAndUserIdIsNotNullAndUserroleExists_ReturnUser() {
        UserroleEntity userrole = mock(UserroleEntity.class);
        Optional<UserroleEntity> userroleOptional = Optional.of((UserroleEntity) userrole);
        UserEntity userEntity = mock(UserEntity.class);

        Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(userroleOptional);
        Mockito.when(userrole.getUser()).thenReturn(userEntity);
        Assertions
            .assertThat(_appService.getUser(userroleId))
            .isEqualTo(_mapper.userEntityToGetUserOutput(userEntity, userrole));
    }

    @Test
    public void GetUser_IfUserroleIdAndUserIdIsNotNullAndUserroleDoesNotExist_ReturnNull() {
        Optional<UserroleEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getUser(userroleId)).isEqualTo(null);
    }

    @Test
    public void ParseUserroleKey_KeysStringIsNotEmptyAndKeyValuePairExists_ReturnUserroleId() {
        String keyString = "roleId=15,userId=15";

        UserroleId userroleId = new UserroleId();
        userroleId.setRoleId(15L);
        userroleId.setUserId(15L);

        Assertions.assertThat(_appService.parseUserroleKey(keyString)).isEqualToComparingFieldByField(userroleId);
    }

    @Test
    public void ParseUserroleKey_KeysStringIsEmpty_ReturnNull() {
        String keyString = "";
        Assertions.assertThat(_appService.parseUserroleKey(keyString)).isEqualTo(null);
    }

    @Test
    public void ParseUserroleKey_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        String keyString = "roleId";

        Assertions.assertThat(_appService.parseUserroleKey(keyString)).isEqualTo(null);
    }
}
