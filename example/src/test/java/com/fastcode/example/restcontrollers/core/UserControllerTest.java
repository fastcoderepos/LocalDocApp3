package com.fastcode.example.restcontrollers.core;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fastcode.example.application.core.authorization.user.UserAppService;
import com.fastcode.example.application.core.authorization.user.dto.*;
import com.fastcode.example.application.core.authorization.userpermission.UserpermissionAppService;
import com.fastcode.example.application.core.authorization.userpermission.UserpermissionAppService;
import com.fastcode.example.application.core.authorization.userrole.UserroleAppService;
import com.fastcode.example.application.core.authorization.userrole.UserroleAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import com.fastcode.example.domain.core.authorization.userpreference.IUserpreferenceManager;
import com.fastcode.example.domain.core.authorization.userpreference.UserpreferenceEntity;
import com.fastcode.example.security.JWTAppService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
public class UserControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("userRepository")
    protected IUserRepository user_repository;

    @SpyBean
    @Qualifier("userAppService")
    protected UserAppService userAppService;

    @SpyBean
    @Qualifier("userpermissionAppService")
    protected UserpermissionAppService userpermissionAppService;

    @SpyBean
    @Qualifier("userroleAppService")
    protected UserroleAppService userroleAppService;

    @SpyBean
    protected IUserpreferenceManager userpreferenceManager;

    @SpyBean
    protected JWTAppService jwtAppService;

    @SpyBean
    protected PasswordEncoder pEncoder;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected UserEntity user;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    static int relationCount = 10;

    @PostConstruct
    public void init() {
        emfs = emf;
    }

    @AfterClass
    public static void cleanup() {
        EntityManager em = emfs.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        em.createNativeQuery("truncate table f_user").executeUpdate();
        em.createNativeQuery("truncate table userpreference").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
    }

    public UserEntity createEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmailAddress("bbc@d.c");
        userEntity.setFirstName("1");
        userEntity.setId(1L);
        userEntity.setIsActive(false);
        userEntity.setIsEmailConfirmed(false);
        userEntity.setLastName("1");
        userEntity.setPassword("1");
        userEntity.setPhoneNumber("1");
        userEntity.setUserName("1");
        userEntity.setVersiono(0L);

        return userEntity;
    }

    public CreateUserInput createUserInput() {
        CreateUserInput userInput = new CreateUserInput();
        userInput.setEmailAddress("abc5@d.c");
        userInput.setFirstName("5");
        userInput.setIsActive(false);
        userInput.setIsEmailConfirmed(false);
        userInput.setLastName("5");
        userInput.setPassword("5");
        userInput.setPhoneNumber("5");
        userInput.setUserName("5");

        return userInput;
    }

    public UserEntity createNewEntity() {
        UserEntity user = new UserEntity();
        user.setEmailAddress("bmc@d.c");
        user.setFirstName("3");
        user.setId(3L);
        user.setIsActive(false);
        user.setIsEmailConfirmed(false);
        user.setLastName("3");
        user.setPassword("3");
        user.setPhoneNumber("3");
        user.setUserName("3");

        return user;
    }

    public UserEntity createUpdateEntity() {
        UserEntity user = new UserEntity();
        user.setEmailAddress("pmk@d.c");
        user.setFirstName("4");
        user.setId(4L);
        user.setIsActive(false);
        user.setIsEmailConfirmed(false);
        user.setLastName("4");
        user.setPassword("4");
        user.setPhoneNumber("4");
        user.setUserName("4");

        return user;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final UserController userController = new UserController(
            userAppService,
            userpermissionAppService,
            userroleAppService,
            pEncoder,
            jwtAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(userController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        user = createEntity();
        List<UserEntity> list = user_repository.findAll();
        if (!list.contains(user)) {
            user = user_repository.save(user);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/user/" + user.getId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () -> mvc.perform(get("/user/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateUser_UserDoesNotExist_ReturnStatusOk() throws Exception {
        Mockito.doReturn(null).when(userAppService).findByUserName(anyString());

        CreateUserInput user = createUserInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user);

        mvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());

        user_repository.delete(createNewEntity());
    }

    @Test
    public void CreateUser_UserAlreadyExists_ThrowEntityExistsException() throws Exception {
        FindUserByNameOutput output = new FindUserByNameOutput();
        output.setEmailAddress("bpc@g.c");
        output.setFirstName("1");
        output.setId(1L);
        output.setIsActive(false);
        output.setLastName("1");
        output.setUserName("1");

        Mockito.doReturn(output).when(userAppService).findByUserName(anyString());
        CreateUserInput user = createUserInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityExistsException("There already exists a user with UserName =" + user.getUserName()));
    }

    @Test
    public void DeleteUser_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(userAppService).findById(999L);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc.perform(delete("/user/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a user with a id=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        UserEntity entity = createNewEntity();
        entity.setVersiono(0L);
        entity = user_repository.save(entity);

        UserpreferenceEntity userpreference = new UserpreferenceEntity();
        userpreference.setId(entity.getId());
        userpreference.setUser(entity);
        userpreference.setTheme("Abc");
        userpreference.setLanguage("abc");
        userpreference = userpreferenceManager.create(userpreference);

        FindUserByIdOutput output = new FindUserByIdOutput();
        output.setEmailAddress(entity.getEmailAddress());
        output.setFirstName(entity.getFirstName());
        output.setId(entity.getId());
        output.setIsActive(entity.getIsActive());
        output.setLastName(entity.getLastName());
        output.setUserName(entity.getUserName());

        Mockito.doReturn(output).when(userAppService).findById(any(Long.class));

        //   Mockito.when(userAppService.findById(any(Long.class))).thenReturn(output);

        mvc
            .perform(delete("/user/" + entity.getId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateUser_UserDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(userAppService).findById(999L);

        UpdateUserInput user = new UpdateUserInput();
        user.setEmailAddress("bmc@g.c");
        user.setFirstName("999");
        user.setId(999L);
        user.setIsActive(false);
        user.setIsEmailConfirmed(false);
        user.setLastName("999");
        user.setPhoneNumber("999");
        user.setUserName("999");

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(put("/user/999").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Unable to update. User with id=999 not found."));
    }

    @Test
    public void UpdateUser_UserExists_ReturnStatusOk() throws Exception {
        UserEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        entity = user_repository.save(entity);
        FindUserWithAllFieldsByIdOutput output = new FindUserWithAllFieldsByIdOutput();
        output.setEmailAddress(entity.getEmailAddress());
        output.setFirstName(entity.getFirstName());
        output.setId(entity.getId());
        output.setIsActive(entity.getIsActive());
        output.setIsEmailConfirmed(entity.getIsEmailConfirmed());
        output.setLastName(entity.getLastName());
        output.setPassword(entity.getPassword());
        output.setPhoneNumber(entity.getPhoneNumber());
        output.setUserName(entity.getUserName());
        output.setVersiono(entity.getVersiono());

        Mockito.when(userAppService.findWithAllFieldsById(entity.getId())).thenReturn(output);
        UpdateUserInput userInput = new UpdateUserInput();
        userInput.setEmailAddress(entity.getEmailAddress());
        userInput.setFirstName(entity.getFirstName());
        userInput.setId(entity.getId());
        userInput.setIsActive(entity.getIsActive());
        userInput.setLastName(entity.getLastName());
        userInput.setPassword(entity.getPassword());
        userInput.setUserName(entity.getUserName());

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userInput);

        mvc
            .perform(put("/user/" + entity.getId() + "/").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        UserEntity de = createUpdateEntity();
        de.setId(entity.getId());
        user_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/user?search=id[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/user?search=userid[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property userid not found!"));
    }

    @Test
    public void GetUserpermissions_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("id", "1");

        Mockito.when(userAppService.parseUserpermissionsJoinColumn("userid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/user/1/userpermissions?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetUserpermissions_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("id", "1");

        Mockito.when(userAppService.parseUserpermissionsJoinColumn("userId")).thenReturn(joinCol);
        mvc
            .perform(
                get("/user/1/userpermissions?search=userId[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetUserpermissions_searchIsNotEmpty() {
        Mockito.when(userAppService.parseUserpermissionsJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/user/1/userpermissions?search=userId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }

    @Test
    public void GetUserroles_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("id", "1");

        Mockito.when(userAppService.parseUserrolesJoinColumn("userid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/user/1/userroles?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetUserroles_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("id", "1");

        Mockito.when(userAppService.parseUserrolesJoinColumn("userId")).thenReturn(joinCol);
        mvc
            .perform(
                get("/user/1/userroles?search=userId[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetUserroles_searchIsNotEmpty() {
        Mockito.when(userAppService.parseUserrolesJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/user/1/userroles?search=userId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }
}
