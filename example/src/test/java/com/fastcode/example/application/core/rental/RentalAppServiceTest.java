package com.fastcode.example.application.core.rental;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fastcode.example.application.core.rental.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.domain.core.customer.CustomerEntity;
import com.fastcode.example.domain.core.customer.ICustomerRepository;
import com.fastcode.example.domain.core.inventory.IInventoryRepository;
import com.fastcode.example.domain.core.inventory.InventoryEntity;
import com.fastcode.example.domain.core.rental.*;
import com.fastcode.example.domain.core.rental.QRentalEntity;
import com.fastcode.example.domain.core.rental.RentalEntity;
import com.fastcode.example.domain.core.staff.IStaffRepository;
import com.fastcode.example.domain.core.staff.StaffEntity;
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
public class RentalAppServiceTest {

    @InjectMocks
    @Spy
    protected RentalAppService _appService;

    @Mock
    protected IRentalRepository _rentalRepository;

    @Mock
    protected ICustomerRepository _customerRepository;

    @Mock
    protected IInventoryRepository _inventoryRepository;

    @Mock
    protected IStaffRepository _staffRepository;

    @Mock
    protected IRentalMapper _mapper;

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
    public void findRentalById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
        Optional<RentalEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_rentalRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.findById(ID)).isEqualTo(null);
    }

    @Test
    public void findRentalById_IdIsNotNullAndIdExists_ReturnRental() {
        RentalEntity rental = mock(RentalEntity.class);
        Optional<RentalEntity> rentalOptional = Optional.of((RentalEntity) rental);
        Mockito.when(_rentalRepository.findById(any(Integer.class))).thenReturn(rentalOptional);

        Assertions.assertThat(_appService.findById(ID)).isEqualTo(_mapper.rentalEntityToFindRentalByIdOutput(rental));
    }

    @Test
    public void createRental_RentalIsNotNullAndRentalDoesNotExist_StoreRental() {
        RentalEntity rentalEntity = mock(RentalEntity.class);
        CreateRentalInput rentalInput = new CreateRentalInput();

        CustomerEntity customer = mock(CustomerEntity.class);
        Optional<CustomerEntity> customerOptional = Optional.of((CustomerEntity) customer);
        rentalInput.setCustomerId(15);

        Mockito.when(_customerRepository.findById(any(Integer.class))).thenReturn(customerOptional);

        InventoryEntity inventory = mock(InventoryEntity.class);
        Optional<InventoryEntity> inventoryOptional = Optional.of((InventoryEntity) inventory);
        rentalInput.setInventoryId(15);

        Mockito.when(_inventoryRepository.findById(any(Integer.class))).thenReturn(inventoryOptional);

        StaffEntity staff = mock(StaffEntity.class);
        Optional<StaffEntity> staffOptional = Optional.of((StaffEntity) staff);
        rentalInput.setStaffId(15);

        Mockito.when(_staffRepository.findById(any(Integer.class))).thenReturn(staffOptional);

        Mockito.when(_mapper.createRentalInputToRentalEntity(any(CreateRentalInput.class))).thenReturn(rentalEntity);
        Mockito.when(_rentalRepository.save(any(RentalEntity.class))).thenReturn(rentalEntity);

        Assertions
            .assertThat(_appService.create(rentalInput))
            .isEqualTo(_mapper.rentalEntityToCreateRentalOutput(rentalEntity));
    }

    @Test
    public void createRental_RentalIsNotNullAndRentalDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        CreateRentalInput rental = mock(CreateRentalInput.class);

        Mockito.when(_mapper.createRentalInputToRentalEntity(any(CreateRentalInput.class))).thenReturn(null);
        Assertions.assertThat(_appService.create(rental)).isEqualTo(null);
    }

    @Test
    public void createRental_RentalIsNotNullAndRentalDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        CreateRentalInput rental = new CreateRentalInput();

        rental.setCustomerId(15);

        Optional<CustomerEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_customerRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.create(rental)).isEqualTo(null);
    }

    @Test
    public void updateRental_RentalIsNotNullAndRentalDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {
        UpdateRentalInput rental = mock(UpdateRentalInput.class);
        RentalEntity rentalEntity = mock(RentalEntity.class);

        Mockito.when(_mapper.updateRentalInputToRentalEntity(any(UpdateRentalInput.class))).thenReturn(rentalEntity);
        Assertions.assertThat(_appService.update(ID, rental)).isEqualTo(null);
    }

    @Test
    public void updateRental_RentalIsNotNullAndRentalDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
        UpdateRentalInput rental = new UpdateRentalInput();
        rental.setCustomerId(15);

        Optional<CustomerEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_customerRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.update(ID, rental)).isEqualTo(null);
    }

    @Test
    public void updateRental_RentalIdIsNotNullAndIdExists_ReturnUpdatedRental() {
        RentalEntity rentalEntity = mock(RentalEntity.class);
        UpdateRentalInput rental = mock(UpdateRentalInput.class);

        Mockito.when(_mapper.updateRentalInputToRentalEntity(any(UpdateRentalInput.class))).thenReturn(rentalEntity);
        Mockito.when(_rentalRepository.save(any(RentalEntity.class))).thenReturn(rentalEntity);
        Assertions
            .assertThat(_appService.update(ID, rental))
            .isEqualTo(_mapper.rentalEntityToUpdateRentalOutput(rentalEntity));
    }

    @Test
    public void deleteRental_RentalIsNotNullAndRentalExists_RentalRemoved() {
        RentalEntity rental = mock(RentalEntity.class);
        Optional<RentalEntity> rentalOptional = Optional.of((RentalEntity) rental);
        Mockito.when(_rentalRepository.findById(any(Integer.class))).thenReturn(rentalOptional);

        _appService.delete(ID);
        verify(_rentalRepository).delete(rental);
    }

    @Test
    public void find_ListIsEmpty_ReturnList() throws Exception {
        List<RentalEntity> list = new ArrayList<>();
        Page<RentalEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindRentalByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_rentalRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void find_ListIsNotEmpty_ReturnList() throws Exception {
        List<RentalEntity> list = new ArrayList<>();
        RentalEntity rental = mock(RentalEntity.class);
        list.add(rental);
        Page<RentalEntity> foundPage = new PageImpl(list);
        Pageable pageable = mock(Pageable.class);
        List<FindRentalByIdOutput> output = new ArrayList<>();
        SearchCriteria search = new SearchCriteria();
        //		search.setType(1);
        //		search.setValue("xyz");
        //		search.setOperator("equals");
        output.add(_mapper.rentalEntityToFindRentalByIdOutput(rental));

        Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
        Mockito.when(_rentalRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(foundPage);
        Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
    }

    @Test
    public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
        QRentalEntity rental = QRentalEntity.rentalEntity;
        SearchFields searchFields = new SearchFields();
        searchFields.setOperator("equals");
        searchFields.setSearchValue("xyz");
        Map<String, SearchFields> map = new HashMap<>();
        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("xyz", String.valueOf(ID));
        BooleanBuilder builder = new BooleanBuilder();
        Assertions.assertThat(_appService.searchKeyValuePair(rental, map, searchMap)).isEqualTo(builder);
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
        QRentalEntity rental = QRentalEntity.rentalEntity;
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
            .searchKeyValuePair(any(QRentalEntity.class), any(HashMap.class), any(HashMap.class));

        Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
    }

    @Test
    public void search_StringIsNull_ReturnNull() throws Exception {
        Assertions.assertThat(_appService.search(null)).isEqualTo(null);
    }

    //Customer
    @Test
    public void GetCustomer_IfRentalIdAndCustomerIdIsNotNullAndRentalExists_ReturnCustomer() {
        RentalEntity rental = mock(RentalEntity.class);
        Optional<RentalEntity> rentalOptional = Optional.of((RentalEntity) rental);
        CustomerEntity customerEntity = mock(CustomerEntity.class);

        Mockito.when(_rentalRepository.findById(any(Integer.class))).thenReturn(rentalOptional);
        Mockito.when(rental.getCustomer()).thenReturn(customerEntity);
        Assertions
            .assertThat(_appService.getCustomer(ID))
            .isEqualTo(_mapper.customerEntityToGetCustomerOutput(customerEntity, rental));
    }

    @Test
    public void GetCustomer_IfRentalIdAndCustomerIdIsNotNullAndRentalDoesNotExist_ReturnNull() {
        Optional<RentalEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_rentalRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getCustomer(ID)).isEqualTo(null);
    }

    //Inventory
    @Test
    public void GetInventory_IfRentalIdAndInventoryIdIsNotNullAndRentalExists_ReturnInventory() {
        RentalEntity rental = mock(RentalEntity.class);
        Optional<RentalEntity> rentalOptional = Optional.of((RentalEntity) rental);
        InventoryEntity inventoryEntity = mock(InventoryEntity.class);

        Mockito.when(_rentalRepository.findById(any(Integer.class))).thenReturn(rentalOptional);
        Mockito.when(rental.getInventory()).thenReturn(inventoryEntity);
        Assertions
            .assertThat(_appService.getInventory(ID))
            .isEqualTo(_mapper.inventoryEntityToGetInventoryOutput(inventoryEntity, rental));
    }

    @Test
    public void GetInventory_IfRentalIdAndInventoryIdIsNotNullAndRentalDoesNotExist_ReturnNull() {
        Optional<RentalEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_rentalRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getInventory(ID)).isEqualTo(null);
    }

    //Staff
    @Test
    public void GetStaff_IfRentalIdAndStaffIdIsNotNullAndRentalExists_ReturnStaff() {
        RentalEntity rental = mock(RentalEntity.class);
        Optional<RentalEntity> rentalOptional = Optional.of((RentalEntity) rental);
        StaffEntity staffEntity = mock(StaffEntity.class);

        Mockito.when(_rentalRepository.findById(any(Integer.class))).thenReturn(rentalOptional);
        Mockito.when(rental.getStaff()).thenReturn(staffEntity);
        Assertions
            .assertThat(_appService.getStaff(ID))
            .isEqualTo(_mapper.staffEntityToGetStaffOutput(staffEntity, rental));
    }

    @Test
    public void GetStaff_IfRentalIdAndStaffIdIsNotNullAndRentalDoesNotExist_ReturnNull() {
        Optional<RentalEntity> nullOptional = Optional.ofNullable(null);
        Mockito.when(_rentalRepository.findById(any(Integer.class))).thenReturn(nullOptional);
        Assertions.assertThat(_appService.getStaff(ID)).isEqualTo(null);
    }

    @Test
    public void ParsepaymentsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull() {
        Map<String, String> joinColumnMap = new HashMap<String, String>();
        String keyString = "15";
        joinColumnMap.put("rentalId", keyString);
        Assertions.assertThat(_appService.parsePaymentsJoinColumn(keyString)).isEqualTo(joinColumnMap);
    }
}
