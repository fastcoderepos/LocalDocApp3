package com.fastcode.example.application.core.filmactor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.example.application.core.filmactor.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.actor.ActorEntity;
import com.fastcode.example.domain.core.actor.IActorRepository;
import com.fastcode.example.domain.core.film.FilmEntity;
import com.fastcode.example.domain.core.film.IFilmRepository;
import com.fastcode.example.domain.core.filmactor.*;
import com.fastcode.example.domain.core.filmactor.FilmActorEntity;
import com.fastcode.example.domain.core.filmactor.FilmActorId;
import com.fastcode.example.domain.core.filmactor.QFilmActorEntity;
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
public class FilmActorAppServiceTest {

    @InjectMocks
    @Spy
    protected FilmActorAppService _appService;

    @Mock
    protected IFilmActorRepository _filmActorRepository;

    @Mock
    protected IActorRepository _actorRepository;

    @Mock
    protected IFilmRepository _filmRepository;

    @Mock
    protected IFilmActorMapper _mapper;

    @Mock
    protected Logger loggerMock;

    @Mock
    protected LoggingHelper logHelper;

    @Mock
    protected FilmActorId filmActorId;

    private static final Long ID = 15L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(_appService);
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());
    }

    @Test
    public void findFilmActorById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<FilmActorEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_filmActorRepository.findById(any(FilmActorId.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(filmActorId)).isEqualTo(null);
    }

    @Test
    public void findFilmActorById_IdIsNotNullAndIdExists_ReturnFilmActor() {
        FilmActorEntity filmActor = mock(FilmActorEntity.class);
        Optional<FilmActorEntity> filmActorOptional = Optional.of((FilmActorEntity) filmActor);
        Mockito.when(_filmActorRepository.findById(any(FilmActorId.class))).thenReturn(filmActorOptional);

        Assertions
            .assertThat(_appService.findById(filmActorId))
            .isEqualTo(_mapper.filmActorEntityToFindFilmActorByIdOutput(filmActor));
    }

    @Test
    public void createFilmActor_FilmActorIsNotNullAndFilmActorDoesNotExist_StoreFilmActor() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        CreateFilmActorInput filmActorInput = new CreateFilmActorInput();

        ActorEntity actor = mock(ActorEntity.class);
        Optional<ActorEntity> actorOptional = Optional.of((ActorEntity) actor);
        filmActorInput.setActorId(15);

        Mockito.when(_actorRepository.findById(any(Integer.class))).thenReturn(actorOptional);

        FilmEntity film = mock(FilmEntity.class);
        Optional<FilmEntity> filmOptional = Optional.of((FilmEntity) film);
        filmActorInput.setFilmId(15);

        Mockito.when(_filmRepository.findById(any(Integer.class))).thenReturn(filmOptional);

        Mockito
            .when(_mapper.createFilmActorInputToFilmActorEntity(any(CreateFilmActorInput.class)))
            .thenReturn(filmActorEntity);
        Mockito.when(_filmActorRepository.save(any(FilmActorEntity.class))).thenReturn(filmActorEntity);

        Assertions
            .assertThat(_appService.create(filmActorInput))
            .isEqualTo(_mapper.filmActorEntityToCreateFilmActorOutput(filmActorEntity));
    }

    @Test
    public void createFilmActor_FilmActorIsNotNullAndFilmActorDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreFilmActor() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        CreateFilmActorInput filmActor = mock(CreateFilmActorInput.class);

        Mockito
            .when(_mapper.createFilmActorInputToFilmActorEntity(any(CreateFilmActorInput.class)))
            .thenReturn(filmActorEntity);
        Mockito.when(_filmActorRepository.save(any(FilmActorEntity.class))).thenReturn(filmActorEntity);
        Assertions
            .assertThat(_appService.create(filmActor))
            .isEqualTo(_mapper.filmActorEntityToCreateFilmActorOutput(filmActorEntity));
    }

    @Test
    public void updateFilmActor_FilmActorIsNotNullAndFilmActorDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedFilmActor() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        UpdateFilmActorInput filmActor = mock(UpdateFilmActorInput.class);

        Mockito
            .when(_mapper.updateFilmActorInputToFilmActorEntity(any(UpdateFilmActorInput.class)))
            .thenReturn(filmActorEntity);
        Mockito.when(_filmActorRepository.save(any(FilmActorEntity.class))).thenReturn(filmActorEntity);
        Assertions
            .assertThat(_appService.update(filmActorId, filmActor))
            .isEqualTo(_mapper.filmActorEntityToUpdateFilmActorOutput(filmActorEntity));
    }

    @Test
    public void updateFilmActor_FilmActorIdIsNotNullAndIdExists_ReturnUpdatedFilmActor() {
        FilmActorEntity filmActorEntity = mock(FilmActorEntity.class);
        UpdateFilmActorInput filmActor = mock(UpdateFilmActorInput.class);

        Mockito
            .when(_mapper.updateFilmActorInputToFilmActorEntity(any(UpdateFilmActorInput.class)))
            .thenReturn(filmActorEntity);
        Mockito.when(_filmActorRepository.save(any(FilmActorEntity.class))).thenReturn(filmActorEntity);
        Assertions
            .assertThat(_appService.update(filmActorId, filmActor))
            .isEqualTo(_mapper.filmActorEntityToUpdateFilmActorOutput(filmActorEntity));
    }

    @Test
    public void deleteFilmActor_FilmActorIsNotNullAndFilmActorExists_FilmActorRemoved() {
        FilmActorEntity filmActor = mock(FilmActorEntity.class);
        Optional<FilmActorEntity> filmActorOptional = Optional.of((FilmActorEntity) filmActor);
        Mockito.when(_filmActorRepository.findById(any(FilmActorId.class))).thenReturn(filmActorOptional);

        _appService.delete(filmActorId);
        verify(_filmActorRepository).delete(filmActor);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<FilmActorEntity> list = new ArrayList<>();
        Page<FilmActorEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindFilmActorByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_filmActorRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<FilmActorEntity> list = new ArrayList<>();
        FilmActorEntity filmActor = mock(FilmActorEntity.class);
        list.add(filmActor);
        Page<FilmActorEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindFilmActorByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.filmActorEntityToFindFilmActorByIdOutput(filmActor));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_filmActorRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QFilmActorEntity filmActor = QFilmActorEntity.filmActorEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        Assertions.assertThat(_appService.searchKeyValuePair(filmActor, map, searchMap)).isEqualTo(builder);
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
        QFilmActorEntity filmActor = QFilmActorEntity.filmActorEntity;
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
            .searchKeyValuePair(any(QFilmActorEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Actor
    @Test
    public void GetActor_IfFilmActorIdAndActorIdIsNotNullAndFilmActorExists_ReturnActor() {
        FilmActorEntity filmActor = mock(FilmActorEntity.class);
        Optional<FilmActorEntity> filmActorOptional = Optional.of((FilmActorEntity) filmActor);
        ActorEntity actorEntity = mock(ActorEntity.class);

        Mockito.when(_filmActorRepository.findById(any(FilmActorId.class))).thenReturn(filmActorOptional);
        Mockito.when(filmActor.getActor()).thenReturn(actorEntity);
        Assertions
            .assertThat(_appService.getActor(filmActorId))
            .isEqualTo(_mapper.actorEntityToGetActorOutput(actorEntity, filmActor));
    }

    @Test
    public void GetActor_IfFilmActorIdAndActorIdIsNotNullAndFilmActorDoesNotExist_ReturnNull() {
        Optional<FilmActorEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_filmActorRepository.findById(any(FilmActorId.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getActor(filmActorId)).isEqualTo(null);
    }

    //Film
    @Test
    public void GetFilm_IfFilmActorIdAndFilmIdIsNotNullAndFilmActorExists_ReturnFilm() {
        FilmActorEntity filmActor = mock(FilmActorEntity.class);
        Optional<FilmActorEntity> filmActorOptional = Optional.of((FilmActorEntity) filmActor);
        FilmEntity filmEntity = mock(FilmEntity.class);

        Mockito.when(_filmActorRepository.findById(any(FilmActorId.class))).thenReturn(filmActorOptional);
        Mockito.when(filmActor.getFilm()).thenReturn(filmEntity);
        Assertions
            .assertThat(_appService.getFilm(filmActorId))
            .isEqualTo(_mapper.filmEntityToGetFilmOutput(filmEntity, filmActor));
    }

    @Test
    public void GetFilm_IfFilmActorIdAndFilmIdIsNotNullAndFilmActorDoesNotExist_ReturnNull() {
        Optional<FilmActorEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_filmActorRepository.findById(any(FilmActorId.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getFilm(filmActorId)).isEqualTo(null);
    }

    @Test
    public void ParseFilmActorKey_KeysStringIsNotEmptyAndKeyValuePairExists_ReturnFilmActorId() {
        String keyString = "actorId=15,filmId=15";

        FilmActorId filmActorId = new FilmActorId();
        filmActorId.setActorId(15);
        filmActorId.setFilmId(15);

        Assertions.assertThat(_appService.parseFilmActorKey(keyString)).isEqualToComparingFieldByField(filmActorId);
    }

    @Test
    public void ParseFilmActorKey_KeysStringIsEmpty_ReturnNull() {
        String keyString = "";
        Assertions.assertThat(_appService.parseFilmActorKey(keyString)).isEqualTo(null);
    }

    @Test
    public void ParseFilmActorKey_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        String keyString = "actorId";

        Assertions.assertThat(_appService.parseFilmActorKey(keyString)).isEqualTo(null);
    }
}
