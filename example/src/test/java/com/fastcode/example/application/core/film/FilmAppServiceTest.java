package com.fastcode.example.application.core.film;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.example.application.core.film.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.film.*;
import com.fastcode.example.domain.core.film.FilmEntity;
import com.fastcode.example.domain.core.film.QFilmEntity;
import com.fastcode.example.domain.core.language.ILanguageRepository;
import com.fastcode.example.domain.core.language.LanguageEntity;
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
public class FilmAppServiceTest {

    @InjectMocks
    @Spy
    protected FilmAppService _appService;

    @Mock
    protected IFilmRepository _filmRepository;

    @Mock
    protected ILanguageRepository _languageRepository;

    @Mock
    protected IFilmMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    protected static Integer ID = 15;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findFilmById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<FilmEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_filmRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findFilmById_IdIsNotNullAndIdExists_ReturnFilm() {
        FilmEntity film = mock(FilmEntity.class);
        Optional<FilmEntity> filmOptional = Optional.of((FilmEntity) film);
        Mockito.when(_filmRepository.findById(any(Integer.class))).thenReturn(filmOptional);

        Assertions.assertThat(_appService.findById(ID)).isEqualTo(_mapper.filmEntityToFindFilmByIdOutput(film));
    }

    @Test
    public void createFilm_FilmIsNotNullAndFilmDoesNotExist_StoreFilm() {
        FilmEntity filmEntity = mock(FilmEntity.class);
        CreateFilmInput filmInput = new CreateFilmInput();

        LanguageEntity language = mock(LanguageEntity.class);
        Optional<LanguageEntity> languageOptional = Optional.of((LanguageEntity) language);
        filmInput.setLanguageId(15);

        Mockito.when(_languageRepository.findById(any(Integer.class))).thenReturn(languageOptional);

        Mockito.when(_mapper.createFilmInputToFilmEntity(any(CreateFilmInput.class))).thenReturn(filmEntity);
        Mockito.when(_filmRepository.save(any(FilmEntity.class))).thenReturn(filmEntity);

        Assertions
            .assertThat(_appService.create(filmInput))
            .isEqualTo(_mapper.filmEntityToCreateFilmOutput(filmEntity));
    }

    @Test
    public void createFilm_FilmIsNotNullAndFilmDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        CreateFilmInput film = mock(CreateFilmInput.class);

        Mockito.when(_mapper.createFilmInputToFilmEntity(any(CreateFilmInput.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(film)).isEqualTo(null);
    }

    @Test
    public void createFilm_FilmIsNotNullAndFilmDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        CreateFilmInput film = new CreateFilmInput();

        film.setLanguageId(15);

        Optional<LanguageEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_languageRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.create(film)).isEqualTo(null);
    }

    @Test
    public void updateFilm_FilmIsNotNullAndFilmDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        UpdateFilmInput film = mock(UpdateFilmInput.class);
        FilmEntity filmEntity = mock(FilmEntity.class);

        Mockito.when(_mapper.updateFilmInputToFilmEntity(any(UpdateFilmInput.class))).thenReturn(filmEntity);
        Assertions.assertThat(_appService.update(ID, film)).isEqualTo(null);
    }

    @Test
    public void updateFilm_FilmIsNotNullAndFilmDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        UpdateFilmInput film = new UpdateFilmInput();
        film.setLanguageId(15);

        Optional<LanguageEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_languageRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.update(ID, film)).isEqualTo(null);
    }

    @Test
    public void updateFilm_FilmIdIsNotNullAndIdExists_ReturnUpdatedFilm() {
        FilmEntity filmEntity = mock(FilmEntity.class);
        UpdateFilmInput film = mock(UpdateFilmInput.class);

        Mockito.when(_mapper.updateFilmInputToFilmEntity(any(UpdateFilmInput.class))).thenReturn(filmEntity);
        Mockito.when(_filmRepository.save(any(FilmEntity.class))).thenReturn(filmEntity);
        Assertions.assertThat(_appService.update(ID, film)).isEqualTo(_mapper.filmEntityToUpdateFilmOutput(filmEntity));
    }

    @Test
    public void deleteFilm_FilmIsNotNullAndFilmExists_FilmRemoved() {
        FilmEntity film = mock(FilmEntity.class);
        Optional<FilmEntity> filmOptional = Optional.of((FilmEntity) film);
        Mockito.when(_filmRepository.findById(any(Integer.class))).thenReturn(filmOptional);

        _appService.delete(ID);
        verify(_filmRepository).delete(film);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<FilmEntity> list = new ArrayList<>();
        Page<FilmEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindFilmByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_filmRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<FilmEntity> list = new ArrayList<>();
        FilmEntity film = mock(FilmEntity.class);
        list.add(film);
        Page<FilmEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindFilmByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.filmEntityToFindFilmByIdOutput(film));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_filmRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QFilmEntity film = QFilmEntity.filmEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("description", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(film.description.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(film, map, searchMap)).isEqualTo(builder);
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
        list.add("description");
        list.add("rating");
        list.add("title");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QFilmEntity film = QFilmEntity.filmEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setFieldName("description");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(film.description.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QFilmEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Language
    @Test
    public void GetLanguage_IfFilmIdAndLanguageIdIsNotNullAndFilmExists_ReturnLanguage() {
        FilmEntity film = mock(FilmEntity.class);
        Optional<FilmEntity> filmOptional = Optional.of((FilmEntity) film);
        LanguageEntity languageEntity = mock(LanguageEntity.class);

        Mockito.when(_filmRepository.findById(any(Integer.class))).thenReturn(filmOptional);
        Mockito.when(film.getLanguage()).thenReturn(languageEntity);
        Assertions
            .assertThat(_appService.getLanguage(ID))
            .isEqualTo(_mapper.languageEntityToGetLanguageOutput(languageEntity, film));
    }

    @Test
    public void GetLanguage_IfFilmIdAndLanguageIdIsNotNullAndFilmDoesNotExist_ReturnNull() {
        Optional<FilmEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_filmRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getLanguage(ID)).isEqualTo(null);
    }

    @Test
    public void ParsefilmActorsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("filmId", keyString);
        Assertions.assertThat(_appService.parseFilmActorsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }

    @Test
    public void ParsefilmCategorysJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("filmId", keyString);
        Assertions.assertThat(_appService.parseFilmCategorysJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }

    @Test
    public void ParseinventorysJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("filmId", keyString);
        Assertions.assertThat(_appService.parseInventorysJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
