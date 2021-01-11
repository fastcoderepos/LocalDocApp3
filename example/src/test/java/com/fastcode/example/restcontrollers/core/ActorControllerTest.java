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

import com.fastcode.example.application.core.actor.ActorAppService;
import com.fastcode.example.application.core.actor.dto.*;
import com.fastcode.example.application.core.filmactor.FilmActorAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.domain.core.actor.ActorEntity;
import com.fastcode.example.domain.core.actor.IActorRepository;
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
public class ActorControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("actorRepository")
    protected IActorRepository actor_repository;

    @SpyBean
    @Qualifier("actorAppService")
    protected ActorAppService actorAppService;

    @SpyBean
    @Qualifier("filmActorAppService")
    protected FilmActorAppService filmActorAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected ActorEntity actor;

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
        em.createNativeQuery("truncate table ACTOR").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
    }

    public ActorEntity createEntity() {
        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setActorId(1);
        actorEntity.setFirstName("1");
        actorEntity.setLastName("1");
        actorEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        actorEntity.setVersiono(0L);

        return actorEntity;
    }

    public CreateActorInput createActorInput() {
        CreateActorInput actorInput = new CreateActorInput();
        actorInput.setActorId(5);
        actorInput.setFirstName("5");
        actorInput.setLastName("5");
        actorInput.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));

        return actorInput;
    }

    public ActorEntity createNewEntity() {
        ActorEntity actor = new ActorEntity();
        actor.setActorId(3);
        actor.setFirstName("3");
        actor.setLastName("3");
        actor.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));

        return actor;
    }

    public ActorEntity createUpdateEntity() {
        ActorEntity actor = new ActorEntity();
        actor.setActorId(4);
        actor.setFirstName("4");
        actor.setLastName("4");
        actor.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));

        return actor;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final ActorController actorController = new ActorController(
            actorAppService,
            filmActorAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(actorController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        actor = createEntity();
        List<ActorEntity> list = actor_repository.findAll();
        if (!list.contains(actor)) {
            actor = actor_repository.save(actor);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/actor/" + actor.getActorId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () -> mvc.perform(get("/actor/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateActor_ActorDoesNotExist_ReturnStatusOk() throws Exception {
        CreateActorInput actorInput = createActorInput();

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(actorInput);

        mvc.perform(post("/actor").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void DeleteActor_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(actorAppService).findById(999);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc.perform(delete("/actor/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a actor with a id=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        ActorEntity entity = createNewEntity();
        entity.setVersiono(0L);
        entity = actor_repository.save(entity);

        FindActorByIdOutput output = new FindActorByIdOutput();
        output.setActorId(entity.getActorId());
        output.setFirstName(entity.getFirstName());
        output.setLastName(entity.getLastName());
        output.setLastUpdate(entity.getLastUpdate());

        Mockito.doReturn(output).when(actorAppService).findById(entity.getActorId());

        //    Mockito.when(actorAppService.findById(entity.getActorId())).thenReturn(output);

        mvc
            .perform(delete("/actor/" + entity.getActorId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateActor_ActorDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(actorAppService).findById(999);

        UpdateActorInput actor = new UpdateActorInput();
        actor.setActorId(999);
        actor.setFirstName("999");
        actor.setLastName("999");
        actor.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(actor);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(put("/actor/999").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Unable to update. Actor with id=999 not found."));
    }

    @Test
    public void UpdateActor_ActorExists_ReturnStatusOk() throws Exception {
        ActorEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        entity = actor_repository.save(entity);
        FindActorByIdOutput output = new FindActorByIdOutput();
        output.setActorId(entity.getActorId());
        output.setFirstName(entity.getFirstName());
        output.setLastName(entity.getLastName());
        output.setLastUpdate(entity.getLastUpdate());
        output.setVersiono(entity.getVersiono());

        Mockito.when(actorAppService.findById(entity.getActorId())).thenReturn(output);

        UpdateActorInput actorInput = new UpdateActorInput();
        actorInput.setActorId(entity.getActorId());
        actorInput.setFirstName(entity.getFirstName());
        actorInput.setLastName(entity.getLastName());
        actorInput.setLastUpdate(entity.getLastUpdate());

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(actorInput);

        mvc
            .perform(put("/actor/" + entity.getActorId() + "/").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        ActorEntity de = createUpdateEntity();
        de.setActorId(entity.getActorId());
        actor_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/actor?search=actorId[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/actor?search=actoractorId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property actoractorId not found!"));
    }

    @Test
    public void GetFilmActors_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("actorId", "1");

        Mockito.when(actorAppService.parseFilmActorsJoinColumn("actorid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/actor/1/filmActors?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetFilmActors_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("actorId", "1");

        Mockito.when(actorAppService.parseFilmActorsJoinColumn("actorId")).thenReturn(joinCol);
        mvc
            .perform(
                get("/actor/1/filmActors?search=actorId[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetFilmActors_searchIsNotEmpty() {
        Mockito.when(actorAppService.parseFilmActorsJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/actor/1/filmActors?search=actorId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }
}
