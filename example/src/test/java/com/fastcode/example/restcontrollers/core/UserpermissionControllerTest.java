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

import com.fastcode.example.application.core.authorization.permission.PermissionAppService;
import com.fastcode.example.application.core.authorization.user.UserAppService;
import com.fastcode.example.application.core.authorization.userpermission.UserpermissionAppService;
import com.fastcode.example.application.core.authorization.userpermission.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.core.authorization.permission.IPermissionRepository;
import com.fastcode.example.domain.core.authorization.permission.PermissionEntity;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import com.fastcode.example.domain.core.authorization.userpermission.IUserpermissionRepository;
import com.fastcode.example.domain.core.authorization.userpermission.UserpermissionEntity;
import com.fastcode.example.domain.core.authorization.userpermission.UserpermissionId;
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
public class UserpermissionControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("userpermissionRepository")
    protected IUserpermissionRepository userpermission_repository;

    @Autowired
    @Qualifier("permissionRepository")
    protected IPermissionRepository permissionRepository;

    @Autowired
    @Qualifier("userRepository")
    protected IUserRepository userRepository;

    @SpyBean
    @Qualifier("userpermissionAppService")
    protected UserpermissionAppService userpermissionAppService;

    @SpyBean
    @Qualifier("permissionAppService")
    protected PermissionAppService permissionAppService;

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

    protected UserpermissionEntity userpermission;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    static int relationCount = 10;

    int countUser = 10;

    int countPermission = 10;

    @PostConstruct
    public void init() {
        emfs = emf;
    }

    @AfterClass
    public static void cleanup() {
        EntityManager em = emfs.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        em.createNativeQuery("truncate table userpermission").executeUpdate();
        em.createNativeQuery("truncate table permission").executeUpdate();
        em.createNativeQuery("truncate table f_user").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
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

    public PermissionEntity createPermissionEntity() {
        if (countPermission > 60) {
            countPermission = 10;
        }

        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setDisplayName(String.valueOf(relationCount));
        permissionEntity.setId(Long.valueOf(relationCount));
        permissionEntity.setName(String.valueOf(relationCount));
        permissionEntity.setVersiono(0L);
        relationCount++;
        if (!permissionRepository.findAll().contains(permissionEntity)) {
            permissionEntity = permissionRepository.save(permissionEntity);
        }
        countPermission++;
        return permissionEntity;
    }

    public UserpermissionEntity createEntity() {
        PermissionEntity permission = createPermissionEntity();
        UserEntity user = createUserEntity();

        UserpermissionEntity userpermissionEntity = new UserpermissionEntity();
        userpermissionEntity.setPermissionId(1L);
        userpermissionEntity.setRevoked(false);
        userpermissionEntity.setUserId(1L);
        userpermissionEntity.setVersiono(0L);
        userpermissionEntity.setPermission(permission);
        userpermissionEntity.setPermissionId(Long.parseLong(permission.getId().toString()));
        userpermissionEntity.setUser(user);
        userpermissionEntity.setUserId(Long.parseLong(user.getId().toString()));

        return userpermissionEntity;
    }

    public CreateUserpermissionInput createUserpermissionInput() {
        CreateUserpermissionInput userpermissionInput = new CreateUserpermissionInput();
        userpermissionInput.setPermissionId(5L);
        userpermissionInput.setRevoked(false);
        userpermissionInput.setUserId(5L);

        return userpermissionInput;
    }

    public UserpermissionEntity createNewEntity() {
        UserpermissionEntity userpermission = new UserpermissionEntity();
        userpermission.setPermissionId(3L);
        userpermission.setRevoked(false);
        userpermission.setUserId(3L);

        return userpermission;
    }

    public UserpermissionEntity createUpdateEntity() {
        UserpermissionEntity userpermission = new UserpermissionEntity();
        userpermission.setPermissionId(4L);
        userpermission.setRevoked(false);
        userpermission.setUserId(4L);

        return userpermission;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final UserpermissionController userpermissionController = new UserpermissionController(
            userpermissionAppService,
            permissionAppService,
            userAppService,
            jwtAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(userpermissionController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        userpermission = createEntity();
        List<UserpermissionEntity> list = userpermission_repository.findAll();
        if (!list.contains(userpermission)) {
            userpermission = userpermission_repository.save(userpermission);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get(
                    "/userpermission/permissionId=" +
                    userpermission.getPermissionId() +
                    ",userId=" +
                    userpermission.getUserId() +
                    "/"
                )
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
                        .perform(
                            get("/userpermission/permissionId=999,userId=999").contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateUserpermission_UserpermissionDoesNotExist_ReturnStatusOk() throws Exception {
        CreateUserpermissionInput userpermissionInput = createUserpermissionInput();

        PermissionEntity permission = createPermissionEntity();

        userpermissionInput.setPermissionId(Long.parseLong(permission.getId().toString()));

        UserEntity user = createUserEntity();

        userpermissionInput.setUserId(Long.parseLong(user.getId().toString()));

        doNothing().when(jwtAppService).deleteAllUserTokens(anyString());
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(userpermissionInput);

        mvc
            .perform(post("/userpermission").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());
    }

    @Test
    public void CreateUserpermission_permissionDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateUserpermissionInput userpermission = createUserpermissionInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(userpermission);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/userpermission").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void CreateUserpermission_userDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateUserpermissionInput userpermission = createUserpermissionInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(userpermission);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/userpermission").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void DeleteUserpermission_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(userpermissionAppService).findById(new UserpermissionId(999L, 999L));
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            delete("/userpermission/permissionId=999,userId=999")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(
                new EntityNotFoundException(
                    "There does not exist a userpermission with a id=permissionId=999,userId=999"
                )
            );
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        UserpermissionEntity entity = createNewEntity();
        entity.setVersiono(0L);
        PermissionEntity permission = createPermissionEntity();
        entity.setPermissionId(Long.parseLong(permission.getId().toString()));
        entity.setPermission(permission);
        UserEntity user = createUserEntity();
        entity.setUserId(Long.parseLong(user.getId().toString()));
        entity.setUser(user);
        entity = userpermission_repository.save(entity);

        FindUserpermissionByIdOutput output = new FindUserpermissionByIdOutput();
        output.setPermissionId(entity.getPermissionId());
        output.setUserId(entity.getUserId());

        //    Mockito.when(userpermissionAppService.findById(new UserpermissionId(entity.getPermissionId(), entity.getUserId()))).thenReturn(output);
        Mockito
            .doReturn(output)
            .when(userpermissionAppService)
            .findById(new UserpermissionId(entity.getPermissionId(), entity.getUserId()));

        doNothing().when(jwtAppService).deleteAllUserTokens(anyString());
        mvc
            .perform(
                delete(
                    "/userpermission/permissionId=" + entity.getPermissionId() + ",userId=" + entity.getUserId() + "/"
                )
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateUserpermission_UserpermissionDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(userpermissionAppService).findById(new UserpermissionId(999L, 999L));

        UpdateUserpermissionInput userpermission = new UpdateUserpermissionInput();
        userpermission.setPermissionId(999L);
        userpermission.setRevoked(false);
        userpermission.setUserId(999L);

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userpermission);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            put("/userpermission/permissionId=999,userId=999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(
                new EntityNotFoundException(
                    "Unable to update. Userpermission with id=permissionId=999,userId=999 not found."
                )
            );
    }

    @Test
    public void UpdateUserpermission_UserpermissionExists_ReturnStatusOk() throws Exception {
        UserpermissionEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        PermissionEntity permission = createPermissionEntity();
        entity.setPermissionId(Long.parseLong(permission.getId().toString()));
        entity.setPermission(permission);
        UserEntity user = createUserEntity();
        entity.setUserId(Long.parseLong(user.getId().toString()));
        entity.setUser(user);
        entity = userpermission_repository.save(entity);
        FindUserpermissionByIdOutput output = new FindUserpermissionByIdOutput();
        output.setPermissionId(entity.getPermissionId());
        output.setRevoked(entity.getRevoked());
        output.setUserId(entity.getUserId());
        output.setVersiono(entity.getVersiono());

        Mockito
            .when(userpermissionAppService.findById(new UserpermissionId(entity.getPermissionId(), entity.getUserId())))
            .thenReturn(output);

        UpdateUserpermissionInput userpermissionInput = new UpdateUserpermissionInput();
        userpermissionInput.setPermissionId(entity.getPermissionId());
        userpermissionInput.setUserId(entity.getUserId());

        doNothing().when(jwtAppService).deleteAllUserTokens(anyString());

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userpermissionInput);

        mvc
            .perform(
                put("/userpermission/permissionId=" + entity.getPermissionId() + ",userId=" + entity.getUserId() + "/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
            .andExpect(status().isOk());

        UserpermissionEntity de = createUpdateEntity();
        de.setPermissionId(entity.getPermissionId());
        de.setUserId(entity.getUserId());
        userpermission_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get("/userpermission?search=permissionId[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/userpermission?search=userpermissionpermissionId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property userpermissionpermissionId not found!"));
    }

    @Test
    public void GetPermission_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/userpermission/permissionId999/permission").contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid id=permissionId999"));
    }

    @Test
    public void GetPermission_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/userpermission/permissionId=999,userId=999/permission")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetPermission_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get(
                    "/userpermission/permissionId=" +
                    userpermission.getPermissionId() +
                    ",userId=" +
                    userpermission.getUserId() +
                    "/permission"
                )
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
                        .perform(get("/userpermission/permissionId999/user").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid id=permissionId999"));
    }

    @Test
    public void GetUser_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/userpermission/permissionId=999,userId=999/user")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetUser_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get(
                    "/userpermission/permissionId=" +
                    userpermission.getPermissionId() +
                    ",userId=" +
                    userpermission.getUserId() +
                    "/user"
                )
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }
}
