package com.fastcode.example.application.core.country;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.example.application.core.country.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.country.*;
import com.fastcode.example.domain.core.country.CountryEntity;
import com.fastcode.example.domain.core.country.QCountryEntity;
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
public class CountryAppServiceTest {

    @InjectMocks
    @Spy
    protected CountryAppService _appService;

    @Mock
    protected ICountryRepository _countryRepository;

    @Mock
    protected ICountryMapper _mapper;

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
    public void findCountryById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<CountryEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_countryRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findCountryById_IdIsNotNullAndIdExists_ReturnCountry() {
        CountryEntity country = mock(CountryEntity.class);
        Optional<CountryEntity> countryOptional = Optional.of((CountryEntity) country);
        Mockito.when(_countryRepository.findById(any(Integer.class))).thenReturn(countryOptional);

        Assertions
            .assertThat(_appService.findById(ID))
            .isEqualTo(_mapper.countryEntityToFindCountryByIdOutput(country));
    }

    @Test
    public void createCountry_CountryIsNotNullAndCountryDoesNotExist_StoreCountry() {
        CountryEntity countryEntity = mock(CountryEntity.class);
        CreateCountryInput countryInput = new CreateCountryInput();

        Mockito
            .when(_mapper.createCountryInputToCountryEntity(any(CreateCountryInput.class)))
            .thenReturn(countryEntity);
        Mockito.when(_countryRepository.save(any(CountryEntity.class))).thenReturn(countryEntity);

        Assertions
            .assertThat(_appService.create(countryInput))
            .isEqualTo(_mapper.countryEntityToCreateCountryOutput(countryEntity));
    }

    @Test
    public void updateCountry_CountryIdIsNotNullAndIdExists_ReturnUpdatedCountry() {
        CountryEntity countryEntity = mock(CountryEntity.class);
        UpdateCountryInput country = mock(UpdateCountryInput.class);

        Mockito
            .when(_mapper.updateCountryInputToCountryEntity(any(UpdateCountryInput.class)))
            .thenReturn(countryEntity);
        Mockito.when(_countryRepository.save(any(CountryEntity.class))).thenReturn(countryEntity);
        Assertions
            .assertThat(_appService.update(ID, country))
            .isEqualTo(_mapper.countryEntityToUpdateCountryOutput(countryEntity));
    }

    @Test
    public void deleteCountry_CountryIsNotNullAndCountryExists_CountryRemoved() {
        CountryEntity country = mock(CountryEntity.class);
        Optional<CountryEntity> countryOptional = Optional.of((CountryEntity) country);
        Mockito.when(_countryRepository.findById(any(Integer.class))).thenReturn(countryOptional);

        _appService.delete(ID);
        verify(_countryRepository).delete(country);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<CountryEntity> list = new ArrayList<>();
        Page<CountryEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindCountryByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_countryRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<CountryEntity> list = new ArrayList<>();
        CountryEntity country = mock(CountryEntity.class);
        list.add(country);
        Page<CountryEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindCountryByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.countryEntityToFindCountryByIdOutput(country));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_countryRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QCountryEntity country = QCountryEntity.countryEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("country", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(country.country.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(country, map, searchMap)).isEqualTo(builder);
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
        list.add("country");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QCountryEntity country = QCountryEntity.countryEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setFieldName("country");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(country.country.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QCountryEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    @Test
    public void ParsecitysJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("countryId", keyString);
        Assertions.assertThat(_appService.parseCitysJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
