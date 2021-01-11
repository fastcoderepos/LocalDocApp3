package com.fastcode.example.restcontrollers.core;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcode.example.application.core.authorization.role.RoleAppService;
import com.fastcode.example.application.core.authorization.user.UserAppService;
import com.fastcode.example.application.core.authorization.userrole.UserroleAppService;
import com.fastcode.example.application.core.authorization.userrole.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.core.authorization.role.IRoleRepository;
import com.fastcode.example.domain.core.authorization.role.RoleEntity;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import com.fastcode.example.domain.core.authorization.userrole.IUserroleRepository;
import com.fastcode.example.domain.core.authorization.userrole.UserroleEntity;
import com.fastcode.example.domain.core.authorization.userrole.UserroleId;
import com.fastcode.example.security.JWTAppService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.env.Environment;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class UserroleControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("userroleRepository")
    protected IUserroleRepository userrole_repository;

    @Autowired
    @Qualifier("roleRepository")
    protected IRoleRepository roleRepository;

    @Autowired
    @Qualifier("userRepository")
    protected IUserRepository userRepository;

    @SpyBean
    @Qualifier("userroleAppService")
    protected UserroleAppService userroleAppService;

    @SpyBean
    @Qualifier("roleAppService")
    protected RoleAppService roleAppService;

    @SpyBean
    @Qualifier("userAppService")
    protected UserAppService userAppService;

    @SpyBean
    protected JWTAppService jwtAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected UserroleEntity userrole;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    static int relationCount = 10;

    int countRole = 10;

    int countUser = 10;

    @PostConstruct
    public void init() {
        emfs = emf;
    }

    @AfterClass
    public static void cleanup() {
        EntityManager em = emfs.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        em.createNativeQuery("truncate table userrole").executeUpdate();
        em.createNativeQuery("truncate table role").executeUpdate();
        em.createNativeQuery("truncate table f_user").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
    }

    public RoleEntity createRoleEntity() {
        if (countRole > 60) {
            countRole = 10;
        }

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setDisplayName(String.valueOf(relationCount));
        roleEntity.setId(Long.valueOf(relationCount));
        roleEntity.setName(String.valueOf(relationCount));
        roleEntity.setVersiono(0L);
        relationCount++;
        if (!roleRepository.findAll().contains(roleEntity)) {
            roleEntity = roleRepository.save(roleEntity);
        }
        countRole++;
        return roleEntity;
    }

    public UserEntity createUserEntity() {
        if (countUser > 60) {
            countUser = 10;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmailAddress(String.valueOf(relationCount));
        userEntity.setFirstName(String.valueOf(relationCount));
        userEntity.setId(Long.valueOf(relationCount));
        userEntity.setIsActive(false);
        userEntity.setIsEmailConfirmed(false);
        userEntity.setLastName(String.valueOf(relationCount));
        userEntity.setPassword(String.valueOf(relationCount));
        userEntity.setPhoneNumber(String.valueOf(relationCount));
        userEntity.setUserName(String.valueOf(relationCount));
        userEntity.setVersiono(0L);
        relationCount++;
        if (!userRepository.findAll().contains(userEntity)) {
            userEntity = userRepository.save(userEntity);
        }
        countUser++;
        return userEntity;
    }

    public UserroleEntity createEntity() {
        RoleEntity role = createRoleEntity();
        UserEntity user = createUserEntity();

        UserroleEntity userroleEntity = new UserroleEntity();
        userroleEntity.setRoleId(1L);
        userroleEntity.setUserId(1L);
        userroleEntity.setVersiono(0L);
        userroleEntity.setRole(role);
        userroleEntity.setRoleId(Long.parseLong(role.getId().toString()));
        userroleEntity.setUser(user);
        userroleEntity.setUserId(Long.parseLong(user.getId().toString()));

        return userroleEntity;
    }

    public CreateUserroleInput createUserroleInput() {
        CreateUserroleInput userroleInput = new CreateUserroleInput();
        userroleInput.setRoleId(5L);
        userroleInput.setUserId(5L);

        return userroleInput;
    }

    public UserroleEntity createNewEntity() {
        UserroleEntity userrole = new UserroleEntity();
        userrole.setRoleId(3L);
        userrole.setUserId(3L);

        return userrole;
    }

    public UserroleEntity createUpdateEntity() {
        UserroleEntity userrole = new UserroleEntity();
        userrole.setRoleId(4L);
        userrole.setUserId(4L);

        return userrole;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final UserroleController userroleController = new UserroleController(
            userroleAppService,
            roleAppService,
            userAppService,
            jwtAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(userroleController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        userrole = createEntity();
        List<UserroleEntity> list = userrole_repository.findAll();
        if (!list.contains(userrole)) {
            userrole = userrole_repository.save(userrole);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get("/userrole/roleId=" + userrole.getRoleId() + ",userId=" + userrole.getUserId() + "/")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/userrole/roleId=999,userId=999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateUserrole_UserroleDoesNotExist_ReturnStatusOk() throws Exception {
        CreateUserroleInput userroleInput = createUserroleInput();

        RoleEntity role = createRoleEntity();

        userroleInput.setRoleId(Long.parseLong(role.getId().toString()));

        UserEntity user = createUserEntity();

        userroleInput.setUserId(Long.parseLong(user.getId().toString()));

        doNothing().when(jwtAppService).deleteAllUserTokens(anyString());
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(userroleInput);

        mvc.perform(post("/userrole").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void CreateUserrole_roleDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateUserroleInput userrole = createUserroleInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(userrole);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/userrole").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void CreateUserrole_userDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateUserroleInput userrole = createUserroleInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(userrole);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/userrole").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void DeleteUserrole_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(userroleAppService).findById(new UserroleId(999L, 999L));
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(delete("/userrole/roleId=999,userId=999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a userrole with a id=roleId=999,userId=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        UserroleEntity entity = createNewEntity();
        entity.setVersiono(0L);
        RoleEntity role = createRoleEntity();
        entity.setRoleId(Long.parseLong(role.getId().toString()));
        entity.setRole(role);
        UserEntity user = createUserEntity();
        entity.setUserId(Long.parseLong(user.getId().toString()));
        entity.setUser(user);
        entity = userrole_repository.save(entity);

        FindUserroleByIdOutput output = new FindUserroleByIdOutput();
        output.setRoleId(entity.getRoleId());
        output.setUserId(entity.getUserId());

        //    Mockito.when(userroleAppService.findById(new UserroleId(entity.getRoleId(), entity.getUserId()))).thenReturn(output);
        Mockito
            .doReturn(output)
            .when(userroleAppService)
            .findById(new UserroleId(entity.getRoleId(), entity.getUserId()));

        doNothing().when(jwtAppService).deleteAllUserTokens(anyString());
        mvc
            .perform(
                delete("/userrole/roleId=" + entity.getRoleId() + ",userId=" + entity.getUserId() + "/")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateUserrole_UserroleDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(userroleAppService).findById(new UserroleId(999L, 999L));

        UpdateUserroleInput userrole = new UpdateUserroleInput();
        userrole.setRoleId(999L);
        userrole.setUserId(999L);

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userrole);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            put("/userrole/roleId=999,userId=999").contentType(MediaType.APPLICATION_JSON).content(json)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(
                new EntityNotFoundException("Unable to update. Userrole with id=roleId=999,userId=999 not found.")
            );
    }

    @Test
    public void UpdateUserrole_UserroleExists_ReturnStatusOk() throws Exception {
        UserroleEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        RoleEntity role = createRoleEntity();
        entity.setRoleId(Long.parseLong(role.getId().toString()));
        entity.setRole(role);
        UserEntity user = createUserEntity();
        entity.setUserId(Long.parseLong(user.getId().toString()));
        entity.setUser(user);
        entity = userrole_repository.save(entity);
        FindUserroleByIdOutput output = new FindUserroleByIdOutput();
        output.setRoleId(entity.getRoleId());
        output.setUserId(entity.getUserId());
        output.setVersiono(entity.getVersiono());

        Mockito
            .when(userroleAppService.findById(new UserroleId(entity.getRoleId(), entity.getUserId())))
            .thenReturn(output);

        UpdateUserroleInput userroleInput = new UpdateUserroleInput();
        userroleInput.setRoleId(entity.getRoleId());
        userroleInput.setUserId(entity.getUserId());

        doNothing().when(jwtAppService).deleteAllUserTokens(anyString());

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userroleInput);

        mvc
            .perform(
                put("/userrole/roleId=" + entity.getRoleId() + ",userId=" + entity.getUserId() + "/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
            .andExpect(status().isOk());

        UserroleEntity de = createUpdateEntity();
        de.setRoleId(entity.getRoleId());
        de.setUserId(entity.getUserId());
        userrole_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/userrole?search=roleId[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/userrole?search=userroleroleId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property userroleroleId not found!"));
    }

    @Test
    public void GetRole_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/userrole/roleId999/role").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid id=roleId999"));
    }

    @Test
    public void GetRole_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/userrole/roleId=999,userId=999/role").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetRole_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get("/userrole/roleId=" + userrole.getRoleId() + ",userId=" + userrole.getUserId() + "/role")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetUser_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/userrole/roleId999/user").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid id=roleId999"));
    }

    @Test
    public void GetUser_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/userrole/roleId=999,userId=999/user").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetUser_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get("/userrole/roleId=" + userrole.getRoleId() + ",userId=" + userrole.getUserId() + "/user")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }
}
