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

import com.fastcode.example.application.core.category.CategoryAppService;
import com.fastcode.example.application.core.category.dto.*;
import com.fastcode.example.application.core.filmcategory.FilmCategoryAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.domain.core.category.CategoryEntity;
import com.fastcode.example.domain.core.category.ICategoryRepository;
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
public class CategoryControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("categoryRepository")
    protected ICategoryRepository category_repository;

    @SpyBean
    @Qualifier("categoryAppService")
    protected CategoryAppService categoryAppService;

    @SpyBean
    @Qualifier("filmCategoryAppService")
    protected FilmCategoryAppService filmCategoryAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected CategoryEntity category;

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
        em.createNativeQuery("truncate table CATEGORY").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
    }

    public CategoryEntity createEntity() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(1);
        categoryEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        categoryEntity.setName("1");
        categoryEntity.setVersiono(0L);

        return categoryEntity;
    }

    public CreateCategoryInput createCategoryInput() {
        CreateCategoryInput categoryInput = new CreateCategoryInput();
        categoryInput.setCategoryId(5);
        categoryInput.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
        categoryInput.setName("5");

        return categoryInput;
    }

    public CategoryEntity createNewEntity() {
        CategoryEntity category = new CategoryEntity();
        category.setCategoryId(3);
        category.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
        category.setName("3");

        return category;
    }

    public CategoryEntity createUpdateEntity() {
        CategoryEntity category = new CategoryEntity();
        category.setCategoryId(4);
        category.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
        category.setName("4");

        return category;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final CategoryController categoryController = new CategoryController(
            categoryAppService,
            filmCategoryAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(categoryController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        category = createEntity();
        List<CategoryEntity> list = category_repository.findAll();
        if (!list.contains(category)) {
            category = category_repository.save(category);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/category/" + category.getCategoryId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc.perform(get("/category/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateCategory_CategoryDoesNotExist_ReturnStatusOk() throws Exception {
        CreateCategoryInput categoryInput = createCategoryInput();

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(categoryInput);

        mvc.perform(post("/category").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void DeleteCategory_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(categoryAppService).findById(999);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(delete("/category/999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a category with a id=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        CategoryEntity entity = createNewEntity();
        entity.setVersiono(0L);
        entity = category_repository.save(entity);

        FindCategoryByIdOutput output = new FindCategoryByIdOutput();
        output.setCategoryId(entity.getCategoryId());
        output.setLastUpdate(entity.getLastUpdate());
        output.setName(entity.getName());

        Mockito.doReturn(output).when(categoryAppService).findById(entity.getCategoryId());

        //    Mockito.when(categoryAppService.findById(entity.getCategoryId())).thenReturn(output);

        mvc
            .perform(delete("/category/" + entity.getCategoryId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateCategory_CategoryDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(categoryAppService).findById(999);

        UpdateCategoryInput category = new UpdateCategoryInput();
        category.setCategoryId(999);
        category.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
        category.setName("999");

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(category);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(put("/category/999").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Unable to update. Category with id=999 not found."));
    }

    @Test
    public void UpdateCategory_CategoryExists_ReturnStatusOk() throws Exception {
        CategoryEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        entity = category_repository.save(entity);
        FindCategoryByIdOutput output = new FindCategoryByIdOutput();
        output.setCategoryId(entity.getCategoryId());
        output.setLastUpdate(entity.getLastUpdate());
        output.setName(entity.getName());
        output.setVersiono(entity.getVersiono());

        Mockito.when(categoryAppService.findById(entity.getCategoryId())).thenReturn(output);

        UpdateCategoryInput categoryInput = new UpdateCategoryInput();
        categoryInput.setCategoryId(entity.getCategoryId());
        categoryInput.setLastUpdate(entity.getLastUpdate());
        categoryInput.setName(entity.getName());

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(categoryInput);

        mvc
            .perform(
                put("/category/" + entity.getCategoryId() + "/").contentType(MediaType.APPLICATION_JSON).content(json)
            )
            .andExpect(status().isOk());

        CategoryEntity de = createUpdateEntity();
        de.setCategoryId(entity.getCategoryId());
        category_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get("/category?search=categoryId[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON)
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
                            get("/category?search=categorycategoryId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property categorycategoryId not found!"));
    }

    @Test
    public void GetFilmCategorys_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("categoryId", "1");

        Mockito.when(categoryAppService.parseFilmCategorysJoinColumn("categoryid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/category/1/filmCategorys?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetFilmCategorys_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("categoryId", "1");

        Mockito.when(categoryAppService.parseFilmCategorysJoinColumn("categoryId")).thenReturn(joinCol);
        mvc
            .perform(
                get("/category/1/filmCategorys?search=categoryId[equals]=1&limit=10&offset=1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetFilmCategorys_searchIsNotEmpty() {
        Mockito.when(categoryAppService.parseFilmCategorysJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/category/1/filmCategorys?search=categoryId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }
}
