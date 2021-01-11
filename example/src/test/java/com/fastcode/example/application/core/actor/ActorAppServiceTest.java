package com.fastcode.example.application.core.actor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.example.application.core.actor.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.actor.*;
import com.fastcode.example.domain.core.actor.ActorEntity;
import com.fastcode.example.domain.core.actor.QActorEntity;
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
public class ActorAppServiceTest {

    @InjectMocks
    @Spy
    protected ActorAppService _appService;

    @Mock
    protected IActorRepository _actorRepository;

    @Mock
    protected IActorMapper _mapper;

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
    public void findActorById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<ActorEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_actorRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findActorById_IdIsNotNullAndIdExists_ReturnActor() {
        ActorEntity actor = mock(ActorEntity.class);
        Optional<ActorEntity> actorOptional = Optional.of((ActorEntity) actor);
        Mockito.when(_actorRepository.findById(any(Integer.class))).thenReturn(actorOptional);

        Assertions.assertThat(_appService.findById(ID)).isEqualTo(_mapper.actorEntityToFindActorByIdOutput(actor));
    }

    @Test
    public void createActor_ActorIsNotNullAndActorDoesNotExist_StoreActor() {
        ActorEntity actorEntity = mock(ActorEntity.class);
        CreateActorInput actorInput = new CreateActorInput();

        Mockito.when(_mapper.createActorInputToActorEntity(any(CreateActorInput.class))).thenReturn(actorEntity);
        Mockito.when(_actorRepository.save(any(ActorEntity.class))).thenReturn(actorEntity);

        Assertions
            .assertThat(_appService.create(actorInput))
            .isEqualTo(_mapper.actorEntityToCreateActorOutput(actorEntity));
    }

    @Test
    public void updateActor_ActorIdIsNotNullAndIdExists_ReturnUpdatedActor() {
        ActorEntity actorEntity = mock(ActorEntity.class);
        UpdateActorInput actor = mock(UpdateActorInput.class);

        Mockito.when(_mapper.updateActorInputToActorEntity(any(UpdateActorInput.class))).thenReturn(actorEntity);
        Mockito.when(_actorRepository.save(any(ActorEntity.class))).thenReturn(actorEntity);
        Assertions
            .assertThat(_appService.update(ID, actor))
            .isEqualTo(_mapper.actorEntityToUpdateActorOutput(actorEntity));
    }

    @Test
    public void deleteActor_ActorIsNotNullAndActorExists_ActorRemoved() {
        ActorEntity actor = mock(ActorEntity.class);
        Optional<ActorEntity> actorOptional = Optional.of((ActorEntity) actor);
        Mockito.when(_actorRepository.findById(any(Integer.class))).thenReturn(actorOptional);

        _appService.delete(ID);
        verify(_actorRepository).delete(actor);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<ActorEntity> list = new ArrayList<>();
        Page<ActorEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindActorByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_actorRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<ActorEntity> list = new ArrayList<>();
        ActorEntity actor = mock(ActorEntity.class);
        list.add(actor);
        Page<ActorEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindActorByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.actorEntityToFindActorByIdOutput(actor));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_actorRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QActorEntity actor = QActorEntity.actorEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("firstName", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(actor.firstName.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(actor, map, searchMap)).isEqualTo(builder);
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
        list.add("firstName");
        list.add("lastName");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QActorEntity actor = QActorEntity.actorEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setFieldName("firstName");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(actor.firstName.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QActorEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    @Test
    public void ParsefilmActorsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("actorId", keyString);
        Assertions.assertThat(_appService.parseFilmActorsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
