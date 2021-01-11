package com.fastcode.example.application.core.language;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.example.application.core.language.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.language.*;
import com.fastcode.example.domain.core.language.LanguageEntity;
import com.fastcode.example.domain.core.language.QLanguageEntity;
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
public class LanguageAppServiceTest {

    @InjectMocks
    @Spy
    protected LanguageAppService _appService;

    @Mock
    protected ILanguageRepository _languageRepository;

    @Mock
    protected ILanguageMapper _mapper;

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
    public void findLanguageById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<LanguageEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_languageRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findLanguageById_IdIsNotNullAndIdExists_ReturnLanguage() {
        LanguageEntity language = mock(LanguageEntity.class);
        Optional<LanguageEntity> languageOptional = Optional.of((LanguageEntity) language);
        Mockito.when(_languageRepository.findById(any(Integer.class))).thenReturn(languageOptional);

        Assertions
            .assertThat(_appService.findById(ID))
            .isEqualTo(_mapper.languageEntityToFindLanguageByIdOutput(language));
    }

    @Test
    public void createLanguage_LanguageIsNotNullAndLanguageDoesNotExist_StoreLanguage() {
        LanguageEntity languageEntity = mock(LanguageEntity.class);
        CreateLanguageInput languageInput = new CreateLanguageInput();

        Mockito
            .when(_mapper.createLanguageInputToLanguageEntity(any(CreateLanguageInput.class)))
            .thenReturn(languageEntity);
        Mockito.when(_languageRepository.save(any(LanguageEntity.class))).thenReturn(languageEntity);

        Assertions
            .assertThat(_appService.create(languageInput))
            .isEqualTo(_mapper.languageEntityToCreateLanguageOutput(languageEntity));
    }

    @Test
    public void updateLanguage_LanguageIdIsNotNullAndIdExists_ReturnUpdatedLanguage() {
        LanguageEntity languageEntity = mock(LanguageEntity.class);
        UpdateLanguageInput language = mock(UpdateLanguageInput.class);

        Mockito
            .when(_mapper.updateLanguageInputToLanguageEntity(any(UpdateLanguageInput.class)))
            .thenReturn(languageEntity);
        Mockito.when(_languageRepository.save(any(LanguageEntity.class))).thenReturn(languageEntity);
        Assertions
            .assertThat(_appService.update(ID, language))
            .isEqualTo(_mapper.languageEntityToUpdateLanguageOutput(languageEntity));
    }

    @Test
    public void deleteLanguage_LanguageIsNotNullAndLanguageExists_LanguageRemoved() {
        LanguageEntity language = mock(LanguageEntity.class);
        Optional<LanguageEntity> languageOptional = Optional.of((LanguageEntity) language);
        Mockito.when(_languageRepository.findById(any(Integer.class))).thenReturn(languageOptional);

        _appService.delete(ID);
        verify(_languageRepository).delete(language);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<LanguageEntity> list = new ArrayList<>();
        Page<LanguageEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindLanguageByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_languageRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<LanguageEntity> list = new ArrayList<>();
        LanguageEntity language = mock(LanguageEntity.class);
        list.add(language);
        Page<LanguageEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindLanguageByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.languageEntityToFindLanguageByIdOutput(language));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_languageRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QLanguageEntity language = QLanguageEntity.languageEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("name", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(language.name.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(language, map, searchMap)).isEqualTo(builder);
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
        list.add("name");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QLanguageEntity language = QLanguageEntity.languageEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setFieldName("name");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(language.name.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QLanguageEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    @Test
    public void ParsefilmsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("languageId", keyString);
        Assertions.assertThat(_appService.parseFilmsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
