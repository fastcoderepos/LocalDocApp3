package com.fastcode.example.application.core.address;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.example.application.core.address.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.address.*;
import com.fastcode.example.domain.core.address.AddressEntity;
import com.fastcode.example.domain.core.address.QAddressEntity;
import com.fastcode.example.domain.core.city.CityEntity;
import com.fastcode.example.domain.core.city.ICityRepository;
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
public class AddressAppServiceTest {

    @InjectMocks
    @Spy
    protected AddressAppService _appService;

    @Mock
    protected IAddressRepository _addressRepository;

    @Mock
    protected ICityRepository _cityRepository;

    @Mock
    protected IAddressMapper _mapper;

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
    public void findAddressById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<AddressEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_addressRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findAddressById_IdIsNotNullAndIdExists_ReturnAddress() {
        AddressEntity address = mock(AddressEntity.class);
        Optional<AddressEntity> addressOptional = Optional.of((AddressEntity) address);
        Mockito.when(_addressRepository.findById(any(Integer.class))).thenReturn(addressOptional);

        Assertions
            .assertThat(_appService.findById(ID))
            .isEqualTo(_mapper.addressEntityToFindAddressByIdOutput(address));
    }

    @Test
    public void createAddress_AddressIsNotNullAndAddressDoesNotExist_StoreAddress() {
        AddressEntity addressEntity = mock(AddressEntity.class);
        CreateAddressInput addressInput = new CreateAddressInput();

        CityEntity city = mock(CityEntity.class);
        Optional<CityEntity> cityOptional = Optional.of((CityEntity) city);
        addressInput.setCityId(15);

        Mockito.when(_cityRepository.findById(any(Integer.class))).thenReturn(cityOptional);

        Mockito
            .when(_mapper.createAddressInputToAddressEntity(any(CreateAddressInput.class)))
            .thenReturn(addressEntity);
        Mockito.when(_addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

        Assertions
            .assertThat(_appService.create(addressInput))
            .isEqualTo(_mapper.addressEntityToCreateAddressOutput(addressEntity));
    }

    @Test
    public void createAddress_AddressIsNotNullAndAddressDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        CreateAddressInput address = mock(CreateAddressInput.class);

        Mockito.when(_mapper.createAddressInputToAddressEntity(any(CreateAddressInput.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(address)).isEqualTo(null);
    }

    @Test
    public void createAddress_AddressIsNotNullAndAddressDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        CreateAddressInput address = new CreateAddressInput();

        address.setCityId(15);

        Optional<CityEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_cityRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.create(address)).isEqualTo(null);
    }

    @Test
    public void updateAddress_AddressIsNotNullAndAddressDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        UpdateAddressInput address = mock(UpdateAddressInput.class);
        AddressEntity addressEntity = mock(AddressEntity.class);

        Mockito
            .when(_mapper.updateAddressInputToAddressEntity(any(UpdateAddressInput.class)))
            .thenReturn(addressEntity);
        Assertions.assertThat(_appService.update(ID, address)).isEqualTo(null);
    }

    @Test
    public void updateAddress_AddressIsNotNullAndAddressDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        UpdateAddressInput address = new UpdateAddressInput();
        address.setCityId(15);

        Optional<CityEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_cityRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.update(ID, address)).isEqualTo(null);
    }

    @Test
    public void updateAddress_AddressIdIsNotNullAndIdExists_ReturnUpdatedAddress() {
        AddressEntity addressEntity = mock(AddressEntity.class);
        UpdateAddressInput address = mock(UpdateAddressInput.class);

        Mockito
            .when(_mapper.updateAddressInputToAddressEntity(any(UpdateAddressInput.class)))
            .thenReturn(addressEntity);
        Mockito.when(_addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);
        Assertions
            .assertThat(_appService.update(ID, address))
            .isEqualTo(_mapper.addressEntityToUpdateAddressOutput(addressEntity));
    }

    @Test
    public void deleteAddress_AddressIsNotNullAndAddressExists_AddressRemoved() {
        AddressEntity address = mock(AddressEntity.class);
        Optional<AddressEntity> addressOptional = Optional.of((AddressEntity) address);
        Mockito.when(_addressRepository.findById(any(Integer.class))).thenReturn(addressOptional);

        _appService.delete(ID);
        verify(_addressRepository).delete(address);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<AddressEntity> list = new ArrayList<>();
        Page<AddressEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindAddressByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_addressRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<AddressEntity> list = new ArrayList<>();
        AddressEntity address = mock(AddressEntity.class);
        list.add(address);
        Page<AddressEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindAddressByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.addressEntityToFindAddressByIdOutput(address));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_addressRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QAddressEntity address = QAddressEntity.addressEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        map.put("address", searchFields);
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(address.address.eq("xyz"));
        Assertions.assertThat(_appService.searchKeyValuePair(address, map, searchMap)).isEqualTo(builder);
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
        list.add("address");
        list.add("address2");
        list.add("district");
        list.add("phone");
        list.add("postalCode");
        _appService.checkProperties(list);
    }

    @Test
    public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
        Map<String, SearchFields> map = new HashMap<>();
        QAddressEntity address = QAddressEntity.addressEntity;
        List<SearchFields> fieldsList = new ArrayList<>();
        SearchFields fields = new SearchFields();
        SearchCriteria search = new SearchCriteria();
        search.setType(3);
        search.setValue("xyz");
        search.setOperator("equals");
        fields.setFieldName("address");
        fields.setOperator("equals");
        fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(address.address.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
        Mockito
            .doReturn(builder)
            .when(_appService)
            .searchKeyValuePair(any(QAddressEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //City
    @Test
    public void GetCity_IfAddressIdAndCityIdIsNotNullAndAddressExists_ReturnCity() {
        AddressEntity address = mock(AddressEntity.class);
        Optional<AddressEntity> addressOptional = Optional.of((AddressEntity) address);
        CityEntity cityEntity = mock(CityEntity.class);

        Mockito.when(_addressRepository.findById(any(Integer.class))).thenReturn(addressOptional);
        Mockito.when(address.getCity()).thenReturn(cityEntity);
        Assertions
            .assertThat(_appService.getCity(ID))
            .isEqualTo(_mapper.cityEntityToGetCityOutput(cityEntity, address));
    }

    @Test
    public void GetCity_IfAddressIdAndCityIdIsNotNullAndAddressDoesNotExist_ReturnNull() {
        Optional<AddressEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_addressRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getCity(ID)).isEqualTo(null);
    }

    @Test
    public void ParsecustomersJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("addressId", keyString);
        Assertions.assertThat(_appService.parseCustomersJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }

    @Test
    public void ParsestaffsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("addressId", keyString);
        Assertions.assertThat(_appService.parseStaffsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }

    @Test
    public void ParsestoresJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("addressId", keyString);
        Assertions.assertThat(_appService.parseStoresJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
