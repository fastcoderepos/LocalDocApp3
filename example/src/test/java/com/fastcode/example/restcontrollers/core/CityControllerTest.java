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

import com.fastcode.example.application.core.address.AddressAppService;
import com.fastcode.example.application.core.city.CityAppService;
import com.fastcode.example.application.core.city.dto.*;
import com.fastcode.example.application.core.country.CountryAppService;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.domain.core.address.AddressEntity;
import com.fastcode.example.domain.core.address.IAddressRepository;
import com.fastcode.example.domain.core.city.CityEntity;
import com.fastcode.example.domain.core.city.CityEntity;
import com.fastcode.example.domain.core.city.ICityRepository;
import com.fastcode.example.domain.core.city.ICityRepository;
import com.fastcode.example.domain.core.country.CountryEntity;
import com.fastcode.example.domain.core.country.ICountryRepository;
import com.fastcode.example.domain.core.customer.CustomerEntity;
import com.fastcode.example.domain.core.customer.ICustomerRepository;
import com.fastcode.example.domain.core.film.FilmEntity;
import com.fastcode.example.domain.core.film.IFilmRepository;
import com.fastcode.example.domain.core.inventory.IInventoryRepository;
import com.fastcode.example.domain.core.inventory.InventoryEntity;
import com.fastcode.example.domain.core.language.ILanguageRepository;
import com.fastcode.example.domain.core.language.LanguageEntity;
import com.fastcode.example.domain.core.rental.IRentalRepository;
import com.fastcode.example.domain.core.rental.RentalEntity;
import com.fastcode.example.domain.core.staff.IStaffRepository;
import com.fastcode.example.domain.core.staff.StaffEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
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
public class CityControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("cityRepository")
    protected ICityRepository city_repository;

    @Autowired
    @Qualifier("countryRepository")
    protected ICountryRepository countryRepository;

    @Autowired
    @Qualifier("addressRepository")
    protected IAddressRepository addressRepository;

    @Autowired
    @Qualifier("rentalRepository")
    protected IRentalRepository rentalRepository;

    @Autowired
    @Qualifier("languageRepository")
    protected ILanguageRepository languageRepository;

    @Autowired
    @Qualifier("customerRepository")
    protected ICustomerRepository customerRepository;

    @Autowired
    @Qualifier("filmRepository")
    protected IFilmRepository filmRepository;

    @Autowired
    @Qualifier("staffRepository")
    protected IStaffRepository staffRepository;

    @Autowired
    @Qualifier("cityRepository")
    protected ICityRepository cityRepository;

    @Autowired
    @Qualifier("inventoryRepository")
    protected IInventoryRepository inventoryRepository;

    @SpyBean
    @Qualifier("cityAppService")
    protected CityAppService cityAppService;

    @SpyBean
    @Qualifier("addressAppService")
    protected AddressAppService addressAppService;

    @SpyBean
    @Qualifier("countryAppService")
    protected CountryAppService countryAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected CityEntity city;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    static int relationCount = 10;

    int countAddress = 10;

    int countRental = 10;

    int countLanguage = 10;

    int countCustomer = 10;

    int countFilm = 10;

    int countStaff = 10;

    int countCountry = 10;

    int countCity = 10;

    int countInventory = 10;

    @PostConstruct
    public void init() {
        emfs = emf;
    }

    @AfterClass
    public static void cleanup() {
        EntityManager em = emfs.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        em.createNativeQuery("truncate table CITY").executeUpdate();
        em.createNativeQuery("truncate table COUNTRY").executeUpdate();
        em.createNativeQuery("truncate table ADDRESS").executeUpdate();
        em.createNativeQuery("truncate table RENTAL").executeUpdate();
        em.createNativeQuery("truncate table LANGUAGE").executeUpdate();
        em.createNativeQuery("truncate table CUSTOMER").executeUpdate();
        em.createNativeQuery("truncate table FILM").executeUpdate();
        em.createNativeQuery("truncate table STAFF").executeUpdate();
        em.createNativeQuery("truncate table CITY").executeUpdate();
        em.createNativeQuery("truncate table INVENTORY").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
    }

    public AddressEntity createAddressEntity() {
        if (countAddress > 60) {
            countAddress = 10;
        }

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddress(String.valueOf(relationCount));
        addressEntity.setAddress2(String.valueOf(relationCount));
        addressEntity.setAddressId(relationCount);
        addressEntity.setDistrict(String.valueOf(relationCount));
        addressEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countAddress + "-09-01 05:25:22"));
        addressEntity.setPhone(String.valueOf(relationCount));
        addressEntity.setPostalCode(String.valueOf(relationCount));
        addressEntity.setVersiono(0L);
        relationCount++;
        CityEntity city = createCityEntity();
        addressEntity.setCity(city);
        if (!addressRepository.findAll().contains(addressEntity)) {
            addressEntity = addressRepository.save(addressEntity);
        }
        countAddress++;
        return addressEntity;
    }

    public RentalEntity createRentalEntity() {
        if (countRental > 60) {
            countRental = 10;
        }

        RentalEntity rentalEntity = new RentalEntity();
        rentalEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countRental + "-09-01 05:25:22"));
        rentalEntity.setRentalDate(SearchUtils.stringToLocalDateTime("19" + countRental + "-09-01 05:25:22"));
        rentalEntity.setRentalId(relationCount);
        rentalEntity.setReturnDate(SearchUtils.stringToLocalDateTime("19" + countRental + "-09-01 05:25:22"));
        rentalEntity.setVersiono(0L);
        relationCount++;
        CustomerEntity customer = createCustomerEntity();
        rentalEntity.setCustomer(customer);
        InventoryEntity inventory = createInventoryEntity();
        rentalEntity.setInventory(inventory);
        StaffEntity staff = createStaffEntity();
        rentalEntity.setStaff(staff);
        if (!rentalRepository.findAll().contains(rentalEntity)) {
            rentalEntity = rentalRepository.save(rentalEntity);
        }
        countRental++;
        return rentalEntity;
    }

    public LanguageEntity createLanguageEntity() {
        if (countLanguage > 60) {
            countLanguage = 10;
        }

        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageId(relationCount);
        languageEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countLanguage + "-09-01 05:25:22"));
        languageEntity.setName(String.valueOf(relationCount));
        languageEntity.setVersiono(0L);
        relationCount++;
        if (!languageRepository.findAll().contains(languageEntity)) {
            languageEntity = languageRepository.save(languageEntity);
        }
        countLanguage++;
        return languageEntity;
    }

    public CustomerEntity createCustomerEntity() {
        if (countCustomer > 60) {
            countCustomer = 10;
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setActive(relationCount);
        customerEntity.setActivebool(false);
        customerEntity.setCreateDate(SearchUtils.stringToLocalDate("19" + countCustomer + "-09-01"));
        customerEntity.setCustomerId(relationCount);
        customerEntity.setEmail(String.valueOf(relationCount));
        customerEntity.setFirstName(String.valueOf(relationCount));
        customerEntity.setLastName(String.valueOf(relationCount));
        customerEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countCustomer + "-09-01 05:25:22"));
        customerEntity.setStoreId((short) relationCount);
        customerEntity.setVersiono(0L);
        relationCount++;
        AddressEntity address = createAddressEntity();
        customerEntity.setAddress(address);
        if (!customerRepository.findAll().contains(customerEntity)) {
            customerEntity = customerRepository.save(customerEntity);
        }
        countCustomer++;
        return customerEntity;
    }

    public FilmEntity createFilmEntity() {
        if (countFilm > 60) {
            countFilm = 10;
        }

        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setDescription(String.valueOf(relationCount));
        filmEntity.setFilmId(relationCount);
        filmEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countFilm + "-09-01 05:25:22"));
        filmEntity.setLength((short) relationCount);
        filmEntity.setRating(String.valueOf(relationCount));
        filmEntity.setReleaseYear(relationCount);
        filmEntity.setRentalDuration((short) relationCount);
        filmEntity.setRentalRate(BigDecimal.valueOf(relationCount));
        filmEntity.setReplacementCost(BigDecimal.valueOf(relationCount));
        filmEntity.setTitle(String.valueOf(relationCount));
        filmEntity.setVersiono(0L);
        relationCount++;
        LanguageEntity language = createLanguageEntity();
        filmEntity.setLanguage(language);
        if (!filmRepository.findAll().contains(filmEntity)) {
            filmEntity = filmRepository.save(filmEntity);
        }
        countFilm++;
        return filmEntity;
    }

    public StaffEntity createStaffEntity() {
        if (countStaff > 60) {
            countStaff = 10;
        }

        StaffEntity staffEntity = new StaffEntity();
        staffEntity.setActive(false);
        staffEntity.setEmail(String.valueOf(relationCount));
        staffEntity.setFirstName(String.valueOf(relationCount));
        staffEntity.setLastName(String.valueOf(relationCount));
        staffEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countStaff + "-09-01 05:25:22"));
        staffEntity.setPassword(String.valueOf(relationCount));
        staffEntity.setStaffId(relationCount);
        staffEntity.setStoreId((short) relationCount);
        staffEntity.setUsername(String.valueOf(relationCount));
        staffEntity.setVersiono(0L);
        relationCount++;
        AddressEntity address = createAddressEntity();
        staffEntity.setAddress(address);
        if (!staffRepository.findAll().contains(staffEntity)) {
            staffEntity = staffRepository.save(staffEntity);
        }
        countStaff++;
        return staffEntity;
    }

    public CountryEntity createCountryEntity() {
        if (countCountry > 60) {
            countCountry = 10;
        }

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCountry(String.valueOf(relationCount));
        countryEntity.setCountryId(relationCount);
        countryEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countCountry + "-09-01 05:25:22"));
        countryEntity.setVersiono(0L);
        relationCount++;
        if (!countryRepository.findAll().contains(countryEntity)) {
            countryEntity = countryRepository.save(countryEntity);
        }
        countCountry++;
        return countryEntity;
    }

    public CityEntity createCityEntity() {
        if (countCity > 60) {
            countCity = 10;
        }

        CityEntity cityEntity = new CityEntity();
        cityEntity.setCity(String.valueOf(relationCount));
        cityEntity.setCityId(relationCount);
        cityEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countCity + "-09-01 05:25:22"));
        cityEntity.setVersiono(0L);
        relationCount++;
        CountryEntity country = createCountryEntity();
        cityEntity.setCountry(country);
        if (!cityRepository.findAll().contains(cityEntity)) {
            cityEntity = cityRepository.save(cityEntity);
        }
        countCity++;
        return cityEntity;
    }

    public InventoryEntity createInventoryEntity() {
        if (countInventory > 60) {
            countInventory = 10;
        }

        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setInventoryId(relationCount);
        inventoryEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countInventory + "-09-01 05:25:22"));
        inventoryEntity.setStoreId((short) relationCount);
        inventoryEntity.setVersiono(0L);
        relationCount++;
        FilmEntity film = createFilmEntity();
        inventoryEntity.setFilm(film);
        if (!inventoryRepository.findAll().contains(inventoryEntity)) {
            inventoryEntity = inventoryRepository.save(inventoryEntity);
        }
        countInventory++;
        return inventoryEntity;
    }

    public CityEntity createEntity() {
        CountryEntity country = createCountryEntity();

        CityEntity cityEntity = new CityEntity();
        cityEntity.setCity("1");
        cityEntity.setCityId(1);
        cityEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        cityEntity.setVersiono(0L);
        cityEntity.setCountry(country);

        return cityEntity;
    }

    public CreateCityInput createCityInput() {
        CreateCityInput cityInput = new CreateCityInput();
        cityInput.setCity("5");
        cityInput.setCityId(5);
        cityInput.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));

        return cityInput;
    }

    public CityEntity createNewEntity() {
        CityEntity city = new CityEntity();
        city.setCity("3");
        city.setCityId(3);
        city.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));

        return city;
    }

    public CityEntity createUpdateEntity() {
        CityEntity city = new CityEntity();
        city.setCity("4");
        city.setCityId(4);
        city.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));

        return city;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final CityController cityController = new CityController(
            cityAppService,
            addressAppService,
            countryAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(cityController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        city = createEntity();
        List<CityEntity> list = city_repository.findAll();
        if (!list.contains(city)) {
            city = city_repository.save(city);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/city/" + city.getCityId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () -> mvc.perform(get("/city/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateCity_CityDoesNotExist_ReturnStatusOk() throws Exception {
        CreateCityInput cityInput = createCityInput();

        CountryEntity country = createCountryEntity();

        cityInput.setCountryId(Integer.parseInt(country.getCountryId().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(cityInput);

        mvc.perform(post("/city").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
    }

    @Test
    public void CreateCity_countryDoesNotExists_ThrowEntityNotFoundException() throws Exception {
        CreateCityInput city = createCityInput();
        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(city);

        org.assertj.core.api.Assertions.assertThatThrownBy(
            () ->
                mvc
                    .perform(post("/city").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isNotFound())
        );
    }

    @Test
    public void DeleteCity_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(cityAppService).findById(999);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc.perform(delete("/city/999").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a city with a id=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        CityEntity entity = createNewEntity();
        entity.setVersiono(0L);
        CountryEntity country = createCountryEntity();
        entity.setCountry(country);
        entity = city_repository.save(entity);

        FindCityByIdOutput output = new FindCityByIdOutput();
        output.setCity(entity.getCity());
        output.setCityId(entity.getCityId());
        output.setLastUpdate(entity.getLastUpdate());

        Mockito.doReturn(output).when(cityAppService).findById(entity.getCityId());

        //    Mockito.when(cityAppService.findById(entity.getCityId())).thenReturn(output);

        mvc
            .perform(delete("/city/" + entity.getCityId() + "/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateCity_CityDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(cityAppService).findById(999);

        UpdateCityInput city = new UpdateCityInput();
        city.setCity("999");
        city.setCityId(999);
        city.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(city);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(put("/city/999").contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Unable to update. City with id=999 not found."));
    }

    @Test
    public void UpdateCity_CityExists_ReturnStatusOk() throws Exception {
        CityEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        CountryEntity country = createCountryEntity();
        entity.setCountry(country);
        entity = city_repository.save(entity);
        FindCityByIdOutput output = new FindCityByIdOutput();
        output.setCity(entity.getCity());
        output.setCityId(entity.getCityId());
        output.setLastUpdate(entity.getLastUpdate());
        output.setVersiono(entity.getVersiono());

        Mockito.when(cityAppService.findById(entity.getCityId())).thenReturn(output);

        UpdateCityInput cityInput = new UpdateCityInput();
        cityInput.setCity(entity.getCity());
        cityInput.setCityId(entity.getCityId());
        cityInput.setLastUpdate(entity.getLastUpdate());

        cityInput.setCountryId(Integer.parseInt(country.getCountryId().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(cityInput);

        mvc
            .perform(put("/city/" + entity.getCityId() + "/").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());

        CityEntity de = createUpdateEntity();
        de.setCityId(entity.getCityId());
        city_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(get("/city?search=cityId[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/city?search=citycityId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property citycityId not found!"));
    }

    @Test
    public void GetAddress_searchIsNotEmptyAndPropertyIsNotValid_ThrowException() {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("cityId", "1");

        Mockito.when(cityAppService.parseAddressJoinColumn("cityid")).thenReturn(joinCol);
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/city/1/address?search=abc[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property abc not found!"));
    }

    @Test
    public void GetAddress_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        Map<String, String> joinCol = new HashMap<String, String>();
        joinCol.put("cityId", "1");

        Mockito.when(cityAppService.parseAddressJoinColumn("cityId")).thenReturn(joinCol);
        mvc
            .perform(
                get("/city/1/address?search=cityId[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetAddress_searchIsNotEmpty() {
        Mockito.when(cityAppService.parseAddressJoinColumn(anyString())).thenReturn(null);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/city/1/address?search=cityId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid join column"));
    }

    @Test
    public void GetCountry_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/city/999/country").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetCountry_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(get("/city/" + city.getCityId() + "/country").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
