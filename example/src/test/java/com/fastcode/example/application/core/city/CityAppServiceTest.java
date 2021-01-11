package com.fastcode.example.application.core.city;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.example.application.core.city.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.city.*;
import com.fastcode.example.domain.core.city.CityEntity;
import com.fastcode.example.domain.core.city.QCityEntity;
import com.fastcode.example.domain.core.country.CountryEntity;
import com.fastcode.example.domain.core.country.ICountryRepository;
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
public class CityAppServiceTest {

    @InjectMocks
    @Spy
    protected CityAppService _appService;

    @Mock
    protected ICityRepository _cityRepository;

    @Mock
    protected ICountryRepository _countryRepository;

    @Mock
    protected ICityMapper _mapper;

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
    public void findCityById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<CityEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_cityRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findCityById_IdIsNotNullAndIdExists_ReturnCity() {
        CityEntity city = mock(CityEntity.class);
        Optional<CityEntity> cityOptional = Optional.of((CityEntity) city);
        Mockito.when(_cityRepository.findById(any(Integer.class))).thenReturn(cityOptional);

        Assertions.assertThat(_appService.findById(ID)).isEqualTo(_mapper.cityEntityToFindCityByIdOutput(city));
    }

    @Test
    public void createCity_CityIsNotNullAndCityDoesNotExist_StoreCity() {
        CityEntity cityEntity = mock(CityEntity.class);
        CreateCityInput cityInput = new CreateCityInput();

        CountryEntity country = mock(CountryEntity.class);
        Optional<CountryEntity> countryOptional = Optional.of((CountryEntity) country);
        cityInput.setCountryId(15);

        Mockito.when(_countryRepository.findById(any(Integer.class))).thenReturn(countryOptional);

        Mockito.when(_mapper.createCityInputToCityEntity(any(CreateCityInput.class))).thenReturn(cityEntity);
        Mockito.when(_cityRepository.save(any(CityEntity.class))).thenReturn(cityEntity);

        Assertions
            .assertThat(_appService.create(cityInput))
            .isEqualTo(_mapper.cityEntityToCreateCityOutput(cityEntity));
    }

    @Test
    public void createCity_CityIsNotNullAndCityDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        CreateCityInput city = mock(CreateCityInput.class);

        Mockito.when(_mapper.createCityInputToCityEntity(any(CreateCityInput.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(city)).isEqualTo(null);
    }

    @Test
    public void createCity_CityIsNotNullAndCityDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        CreateCityInput city = new CreateCityInput();

        city.setCountryId(15);

        Optional<CountryEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_countryRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.create(city)).isEqualTo(null);
    }

    @Test
    public void updateCity_CityIsNotNullAndCityDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        UpdateCityInput city = mock(UpdateCityInput.class);
        CityEntity cityEntity = mock(CityEntity.class);

        Mockito.when(_mapper.updateCityInputToCityEntity(any(UpdateCityInput.class))).thenReturn(cityEntity);
        Assertions.assertThat(_appService.update(ID, city)).isEqualTo(null);
    }

    @Test
    public void updateCity_CityIsNotNullAndCityDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        UpdateCityInput city = new UpdateCityInput();
        city.setCountryId(15);

        Optional<CountryEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_countryRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.update(ID, city)).isEqualTo(null);
    }

    @Test
    public void updateCity_CityIdIsNotNullAndIdExists_ReturnUpdatedCity() {
        CityEntity cityEntity = mock(CityEntity.class);
        UpdateCityInput city = mock(UpdateCityInput.class);

        Mockito.when(_mapper.updateCityInputToCityEntity(any(UpdateCityInput.class))).thenReturn(cityEntity);
        Mockito.when(_cityRepository.save(any(CityEntity.class))).thenReturn(cityEntity);
        Assertions.assertThat(_appService.update(ID, city)).isEqualTo(_mapper.cityEntityToUpdateCityOutput(cityEntity));
    }

    @Test
    public void deleteCity_CityIsNotNullAndCityExists_CityRemoved() {
        CityEntity city = mock(CityEntity.class);
        Optional<CityEntity> cityOptional = Optional.of((CityEntity) city);
        Mockito.when(_cityRepository.findById(any(Integer.class))).thenReturn(cityOptional);

        _appService.delete(ID);
        verify(_cityRepository).delete(city);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<CityEntity> list = new ArrayList<>();
        Page<CityEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindCityByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_cityRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<CityEntity> list = new ArrayList<>();
        CityEntity city = mock(CityEntity.class);
        list.add(city);
        Page<CityEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindCityByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.cityEntityToFindCityByIdOutput(city));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_cityRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QCityEntity city = QCityEntity.cityEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("city", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(city.city.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(city, map, searchMap)).isEqualTo(builder);
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
        list.add("city");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QCityEntity city = QCityEntity.cityEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setFieldName("city");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(city.city.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QCityEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Country
    @Test
    public void GetCountry_IfCityIdAndCountryIdIsNotNullAndCityExists_ReturnCountry() {
        CityEntity city = mock(CityEntity.class);
        Optional<CityEntity> cityOptional = Optional.of((CityEntity) city);
        CountryEntity countryEntity = mock(CountryEntity.class);

        Mockito.when(_cityRepository.findById(any(Integer.class))).thenReturn(cityOptional);
        Mockito.when(city.getCountry()).thenReturn(countryEntity);
        Assertions
            .assertThat(_appService.getCountry(ID))
            .isEqualTo(_mapper.countryEntityToGetCountryOutput(countryEntity, city));
    }

    @Test
    public void GetCountry_IfCityIdAndCountryIdIsNotNullAndCityDoesNotExist_ReturnNull() {
        Optional<CityEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_cityRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getCountry(ID)).isEqualTo(null);
    }

    @Test
    public void ParseaddressJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("cityId", keyString);
        Assertions.assertThat(_appService.parseAddressJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
