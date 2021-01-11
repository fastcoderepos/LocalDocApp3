package com.fastcode.example.application.core.filmcategory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.example.application.core.filmcategory.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.category.CategoryEntity;
import com.fastcode.example.domain.core.category.ICategoryRepository;
import com.fastcode.example.domain.core.film.FilmEntity;
import com.fastcode.example.domain.core.film.IFilmRepository;
import com.fastcode.example.domain.core.filmcategory.*;
import com.fastcode.example.domain.core.filmcategory.FilmCategoryEntity;
import com.fastcode.example.domain.core.filmcategory.FilmCategoryId;
import com.fastcode.example.domain.core.filmcategory.QFilmCategoryEntity;
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
public class FilmCategoryAppServiceTest {

    @InjectMocks
    @Spy
    protected FilmCategoryAppService _appService;

    @Mock
    protected IFilmCategoryRepository _filmCategoryRepository;

    @Mock
    protected ICategoryRepository _categoryRepository;

    @Mock
    protected IFilmRepository _filmRepository;

    @Mock
    protected IFilmCategoryMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected FilmCategoryId filmCategoryId;

    private static final Long ID = 15L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findFilmCategoryById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<FilmCategoryEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_filmCategoryRepository.findById(any(FilmCategoryId.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(filmCategoryId)).isEqualTo(null);
    }

    @Test
    public void findFilmCategoryById_IdIsNotNullAndIdExists_ReturnFilmCategory() {
        FilmCategoryEntity filmCategory = mock(FilmCategoryEntity.class);
        Optional<FilmCategoryEntity> filmCategoryOptional = Optional.of((FilmCategoryEntity) filmCategory);
        Mockito.when(_filmCategoryRepository.findById(any(FilmCategoryId.class))).thenReturn(filmCategoryOptional);

        Assertions
            .assertThat(_appService.findById(filmCategoryId))
            .isEqualTo(_mapper.filmCategoryEntityToFindFilmCategoryByIdOutput(filmCategory));
    }

    @Test
    public void createFilmCategory_FilmCategoryIsNotNullAndFilmCategoryDoesNotExist_StoreFilmCategory() {
        FilmCategoryEntity filmCategoryEntity = mock(FilmCategoryEntity.class);
        CreateFilmCategoryInput filmCategoryInput = new CreateFilmCategoryInput();

        CategoryEntity category = mock(CategoryEntity.class);
        Optional<CategoryEntity> categoryOptional = Optional.of((CategoryEntity) category);
        filmCategoryInput.setCategoryId(15);

        Mockito.when(_categoryRepository.findById(any(Integer.class))).thenReturn(categoryOptional);

        FilmEntity film = mock(FilmEntity.class);
        Optional<FilmEntity> filmOptional = Optional.of((FilmEntity) film);
        filmCategoryInput.setFilmId(15);

        Mockito.when(_filmRepository.findById(any(Integer.class))).thenReturn(filmOptional);

        Mockito
            .when(_mapper.createFilmCategoryInputToFilmCategoryEntity(any(CreateFilmCategoryInput.class)))
            .thenReturn(filmCategoryEntity);
        Mockito.when(_filmCategoryRepository.save(any(FilmCategoryEntity.class))).thenReturn(filmCategoryEntity);

        Assertions
            .assertThat(_appService.create(filmCategoryInput))
            .isEqualTo(_mapper.filmCategoryEntityToCreateFilmCategoryOutput(filmCategoryEntity));
    }

    @Test
    public void createFilmCategory_FilmCategoryIsNotNullAndFilmCategoryDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreFilmCategory() {
        FilmCategoryEntity filmCategoryEntity = mock(FilmCategoryEntity.class);
        CreateFilmCategoryInput filmCategory = mock(CreateFilmCategoryInput.class);

        Mockito
            .when(_mapper.createFilmCategoryInputToFilmCategoryEntity(any(CreateFilmCategoryInput.class)))
            .thenReturn(filmCategoryEntity);
        Mockito.when(_filmCategoryRepository.save(any(FilmCategoryEntity.class))).thenReturn(filmCategoryEntity);
        Assertions
            .assertThat(_appService.create(filmCategory))
            .isEqualTo(_mapper.filmCategoryEntityToCreateFilmCategoryOutput(filmCategoryEntity));
    }

    @Test
    public void updateFilmCategory_FilmCategoryIsNotNullAndFilmCategoryDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedFilmCategory() {
        FilmCategoryEntity filmCategoryEntity = mock(FilmCategoryEntity.class);
        UpdateFilmCategoryInput filmCategory = mock(UpdateFilmCategoryInput.class);

        Mockito
            .when(_mapper.updateFilmCategoryInputToFilmCategoryEntity(any(UpdateFilmCategoryInput.class)))
            .thenReturn(filmCategoryEntity);
        Mockito.when(_filmCategoryRepository.save(any(FilmCategoryEntity.class))).thenReturn(filmCategoryEntity);
        Assertions
            .assertThat(_appService.update(filmCategoryId, filmCategory))
            .isEqualTo(_mapper.filmCategoryEntityToUpdateFilmCategoryOutput(filmCategoryEntity));
    }

    @Test
    public void updateFilmCategory_FilmCategoryIdIsNotNullAndIdExists_ReturnUpdatedFilmCategory() {
        FilmCategoryEntity filmCategoryEntity = mock(FilmCategoryEntity.class);
        UpdateFilmCategoryInput filmCategory = mock(UpdateFilmCategoryInput.class);

        Mockito
            .when(_mapper.updateFilmCategoryInputToFilmCategoryEntity(any(UpdateFilmCategoryInput.class)))
            .thenReturn(filmCategoryEntity);
        Mockito.when(_filmCategoryRepository.save(any(FilmCategoryEntity.class))).thenReturn(filmCategoryEntity);
        Assertions
            .assertThat(_appService.update(filmCategoryId, filmCategory))
            .isEqualTo(_mapper.filmCategoryEntityToUpdateFilmCategoryOutput(filmCategoryEntity));
    }

    @Test
    public void deleteFilmCategory_FilmCategoryIsNotNullAndFilmCategoryExists_FilmCategoryRemoved() {
        FilmCategoryEntity filmCategory = mock(FilmCategoryEntity.class);
        Optional<FilmCategoryEntity> filmCategoryOptional = Optional.of((FilmCategoryEntity) filmCategory);
        Mockito.when(_filmCategoryRepository.findById(any(FilmCategoryId.class))).thenReturn(filmCategoryOptional);

        _appService.delete(filmCategoryId);
        verify(_filmCategoryRepository).delete(filmCategory);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<FilmCategoryEntity> list = new ArrayList<>();
        Page<FilmCategoryEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindFilmCategoryByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_filmCategoryRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<FilmCategoryEntity> list = new ArrayList<>();
        FilmCategoryEntity filmCategory = mock(FilmCategoryEntity.class);
        list.add(filmCategory);
        Page<FilmCategoryEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindFilmCategoryByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.filmCategoryEntityToFindFilmCategoryByIdOutput(filmCategory));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_filmCategoryRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QFilmCategoryEntity filmCategory = QFilmCategoryEntity.filmCategoryEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        Assertions.assertThat(_appService.searchKeyValuePair(filmCategory, map, searchMap)).isEqualTo(builder);
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
        QFilmCategoryEntity filmCategory = QFilmCategoryEntity.filmCategoryEntity;
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
            .searchKeyValuePair(any(QFilmCategoryEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Category
    @Test
    public void GetCategory_IfFilmCategoryIdAndCategoryIdIsNotNullAndFilmCategoryExists_ReturnCategory() {
        FilmCategoryEntity filmCategory = mock(FilmCategoryEntity.class);
        Optional<FilmCategoryEntity> filmCategoryOptional = Optional.of((FilmCategoryEntity) filmCategory);
        CategoryEntity categoryEntity = mock(CategoryEntity.class);

        Mockito.when(_filmCategoryRepository.findById(any(FilmCategoryId.class))).thenReturn(filmCategoryOptional);
        Mockito.when(filmCategory.getCategory()).thenReturn(categoryEntity);
        Assertions
            .assertThat(_appService.getCategory(filmCategoryId))
            .isEqualTo(_mapper.categoryEntityToGetCategoryOutput(categoryEntity, filmCategory));
    }

    @Test
    public void GetCategory_IfFilmCategoryIdAndCategoryIdIsNotNullAndFilmCategoryDoesNotExist_ReturnNull() {
        Optional<FilmCategoryEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_filmCategoryRepository.findById(any(FilmCategoryId.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getCategory(filmCategoryId)).isEqualTo(null);
    }

    //Film
    @Test
    public void GetFilm_IfFilmCategoryIdAndFilmIdIsNotNullAndFilmCategoryExists_ReturnFilm() {
        FilmCategoryEntity filmCategory = mock(FilmCategoryEntity.class);
        Optional<FilmCategoryEntity> filmCategoryOptional = Optional.of((FilmCategoryEntity) filmCategory);
        FilmEntity filmEntity = mock(FilmEntity.class);

        Mockito.when(_filmCategoryRepository.findById(any(FilmCategoryId.class))).thenReturn(filmCategoryOptional);
        Mockito.when(filmCategory.getFilm()).thenReturn(filmEntity);
        Assertions
            .assertThat(_appService.getFilm(filmCategoryId))
            .isEqualTo(_mapper.filmEntityToGetFilmOutput(filmEntity, filmCategory));
    }

    @Test
    public void GetFilm_IfFilmCategoryIdAndFilmIdIsNotNullAndFilmCategoryDoesNotExist_ReturnNull() {
        Optional<FilmCategoryEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_filmCategoryRepository.findById(any(FilmCategoryId.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getFilm(filmCategoryId)).isEqualTo(null);
    }

    @Test
    public void ParseFilmCategoryKey_KeysStringIsNotEmptyAndKeyValuePairExists_ReturnFilmCategoryId() {
        String keyString = "categoryId=15,filmId=15";

        FilmCategoryId filmCategoryId = new FilmCategoryId();
        filmCategoryId.setCategoryId(15);
        filmCategoryId.setFilmId(15);

        Assertions
            .assertThat(_appService.parseFilmCategoryKey(keyString))
            .isEqualToComparingFieldByField(filmCategoryId);
    }

    @Test
    public void ParseFilmCategoryKey_KeysStringIsEmpty_ReturnNull() {
        String keyString = "";
        Assertions.assertThat(_appService.parseFilmCategoryKey(keyString)).isEqualTo(null);
    }

    @Test
    public void ParseFilmCategoryKey_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        String keyString = "categoryId";

        Assertions.assertThat(_appService.parseFilmCategoryKey(keyString)).isEqualTo(null);
    }
}
