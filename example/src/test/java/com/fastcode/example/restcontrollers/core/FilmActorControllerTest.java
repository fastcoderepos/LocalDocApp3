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

import com.fastcode.example.application.core.actor.ActorAppService;
import com.fastcode.example.application.core.film.FilmAppService;
import com.fastcode.example.application.core.filmactor.FilmActorAppService;
import com.fastcode.example.application.core.filmactor.dto.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.domain.core.actor.ActorEntity;
import com.fastcode.example.domain.core.actor.IActorRepository;
import com.fastcode.example.domain.core.address.AddressEntity;
import com.fastcode.example.domain.core.address.IAddressRepository;
import com.fastcode.example.domain.core.city.CityEntity;
import com.fastcode.example.domain.core.city.ICityRepository;
import com.fastcode.example.domain.core.country.CountryEntity;
import com.fastcode.example.domain.core.country.ICountryRepository;
import com.fastcode.example.domain.core.customer.CustomerEntity;
import com.fastcode.example.domain.core.customer.ICustomerRepository;
import com.fastcode.example.domain.core.film.FilmEntity;
import com.fastcode.example.domain.core.film.IFilmRepository;
import com.fastcode.example.domain.core.filmactor.FilmActorEntity;
import com.fastcode.example.domain.core.filmactor.FilmActorId;
import com.fastcode.example.domain.core.filmactor.IFilmActorRepository;
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
public class FilmActorControllerTest {

    @Autowired
    protected SortHandlerMethodArgumentResolver sortArgumentResolver;

    @Autowired
    @Qualifier("filmActorRepository")
    protected IFilmActorRepository filmActor_repository;

    @Autowired
    @Qualifier("actorRepository")
    protected IActorRepository actorRepository;

    @Autowired
    @Qualifier("filmRepository")
    protected IFilmRepository filmRepository;

    @Autowired
    @Qualifier("languageRepository")
    protected ILanguageRepository languageRepository;

    @Autowired
    @Qualifier("rentalRepository")
    protected IRentalRepository rentalRepository;

    @Autowired
    @Qualifier("addressRepository")
    protected IAddressRepository addressRepository;

    @Autowired
    @Qualifier("customerRepository")
    protected ICustomerRepository customerRepository;

    @Autowired
    @Qualifier("staffRepository")
    protected IStaffRepository staffRepository;

    @Autowired
    @Qualifier("countryRepository")
    protected ICountryRepository countryRepository;

    @Autowired
    @Qualifier("cityRepository")
    protected ICityRepository cityRepository;

    @Autowired
    @Qualifier("inventoryRepository")
    protected IInventoryRepository inventoryRepository;

    @SpyBean
    @Qualifier("filmActorAppService")
    protected FilmActorAppService filmActorAppService;

    @SpyBean
    @Qualifier("actorAppService")
    protected ActorAppService actorAppService;

    @SpyBean
    @Qualifier("filmAppService")
    protected FilmAppService filmAppService;

    @SpyBean
    protected LoggingHelper logHelper;

    @SpyBean
    protected Environment env;

    @Mock
    protected Logger loggerMock;

    protected FilmActorEntity filmActor;

    protected MockMvc mvc;

    @Autowired
    EntityManagerFactory emf;

    static EntityManagerFactory emfs;

    static int relationCount = 10;

    int countLanguage = 10;

    int countRental = 10;

    int countAddress = 10;

    int countFilm = 10;

    int countCustomer = 10;

    int countActor = 10;

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
        em.createNativeQuery("truncate table FILM_ACTOR").executeUpdate();
        em.createNativeQuery("truncate table ACTOR").executeUpdate();
        em.createNativeQuery("truncate table FILM").executeUpdate();
        em.createNativeQuery("truncate table LANGUAGE").executeUpdate();
        em.createNativeQuery("truncate table RENTAL").executeUpdate();
        em.createNativeQuery("truncate table ADDRESS").executeUpdate();
        em.createNativeQuery("truncate table CUSTOMER").executeUpdate();
        em.createNativeQuery("truncate table STAFF").executeUpdate();
        em.createNativeQuery("truncate table COUNTRY").executeUpdate();
        em.createNativeQuery("truncate table CITY").executeUpdate();
        em.createNativeQuery("truncate table INVENTORY").executeUpdate();
        em.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
        em.getTransaction().commit();
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

    public ActorEntity createActorEntity() {
        if (countActor > 60) {
            countActor = 10;
        }

        ActorEntity actorEntity = new ActorEntity();
        actorEntity.setActorId(relationCount);
        actorEntity.setFirstName(String.valueOf(relationCount));
        actorEntity.setLastName(String.valueOf(relationCount));
        actorEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("19" + countActor + "-09-01 05:25:22"));
        actorEntity.setVersiono(0L);
        relationCount++;
        if (!actorRepository.findAll().contains(actorEntity)) {
            actorEntity = actorRepository.save(actorEntity);
        }
        countActor++;
        return actorEntity;
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

    public FilmActorEntity createEntity() {
        ActorEntity actor = createActorEntity();
        FilmEntity film = createFilmEntity();

        FilmActorEntity filmActorEntity = new FilmActorEntity();
        filmActorEntity.setActorId(1);
        filmActorEntity.setFilmId(1);
        filmActorEntity.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        filmActorEntity.setVersiono(0L);
        filmActorEntity.setActor(actor);
        filmActorEntity.setActorId(Integer.parseInt(actor.getActorId().toString()));
        filmActorEntity.setFilm(film);
        filmActorEntity.setFilmId(Integer.parseInt(film.getFilmId().toString()));

        return filmActorEntity;
    }

    public CreateFilmActorInput createFilmActorInput() {
        CreateFilmActorInput filmActorInput = new CreateFilmActorInput();
        filmActorInput.setActorId(5);
        filmActorInput.setFilmId(5);
        filmActorInput.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));

        return filmActorInput;
    }

    public FilmActorEntity createNewEntity() {
        FilmActorEntity filmActor = new FilmActorEntity();
        filmActor.setActorId(3);
        filmActor.setFilmId(3);
        filmActor.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));

        return filmActor;
    }

    public FilmActorEntity createUpdateEntity() {
        FilmActorEntity filmActor = new FilmActorEntity();
        filmActor.setActorId(4);
        filmActor.setFilmId(4);
        filmActor.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));

        return filmActor;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final FilmActorController filmActorController = new FilmActorController(
            filmActorAppService,
            actorAppService,
            filmAppService,
            logHelper,
            env
        );
        when(logHelper.getLogger()).thenReturn(loggerMock);
        doNothing().when(loggerMock).error(anyString());

        this.mvc =
            MockMvcBuilders
                .standaloneSetup(filmActorController)
                .setCustomArgumentResolvers(sortArgumentResolver)
                .setControllerAdvice()
                .build();
    }

    @Before
    public void initTest() {
        filmActor = createEntity();
        List<FilmActorEntity> list = filmActor_repository.findAll();
        if (!list.contains(filmActor)) {
            filmActor = filmActor_repository.save(filmActor);
        }
    }

    @Test
    public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get("/filmActor/actorId=" + filmActor.getActorId() + ",filmId=" + filmActor.getFilmId() + "/")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void FindById_IdIsNotValid_ReturnStatusNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/filmActor/actorId=999,filmId=999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void CreateFilmActor_FilmActorDoesNotExist_ReturnStatusOk() throws Exception {
        CreateFilmActorInput filmActorInput = createFilmActorInput();

        ActorEntity actor = createActorEntity();

        filmActorInput.setActorId(Integer.parseInt(actor.getActorId().toString()));

        FilmEntity film = createFilmEntity();

        filmActorInput.setFilmId(Integer.parseInt(film.getFilmId().toString()));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();

        String json = ow.writeValueAsString(filmActorInput);

        mvc
            .perform(post("/filmActor").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk());
    }

    @Test
    public void DeleteFilmActor_IdIsNotValid_ThrowEntityNotFoundException() {
        doReturn(null).when(filmActorAppService).findById(new FilmActorId(999, 999));
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(delete("/filmActor/actorId=999,filmId=999").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("There does not exist a filmActor with a id=actorId=999,filmId=999"));
    }

    @Test
    public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
        FilmActorEntity entity = createNewEntity();
        entity.setVersiono(0L);
        ActorEntity actor = createActorEntity();
        entity.setActorId(Integer.parseInt(actor.getActorId().toString()));
        entity.setActor(actor);
        FilmEntity film = createFilmEntity();
        entity.setFilmId(Integer.parseInt(film.getFilmId().toString()));
        entity.setFilm(film);
        entity = filmActor_repository.save(entity);

        FindFilmActorByIdOutput output = new FindFilmActorByIdOutput();
        output.setActorId(entity.getActorId());
        output.setFilmId(entity.getFilmId());
        output.setLastUpdate(entity.getLastUpdate());

        //    Mockito.when(filmActorAppService.findById(new FilmActorId(entity.getActorId(), entity.getFilmId()))).thenReturn(output);
        Mockito
            .doReturn(output)
            .when(filmActorAppService)
            .findById(new FilmActorId(entity.getActorId(), entity.getFilmId()));

        mvc
            .perform(
                delete("/filmActor/actorId=" + entity.getActorId() + ",filmId=" + entity.getFilmId() + "/")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());
    }

    @Test
    public void UpdateFilmActor_FilmActorDoesNotExist_ReturnStatusNotFound() throws Exception {
        doReturn(null).when(filmActorAppService).findById(new FilmActorId(999, 999));

        UpdateFilmActorInput filmActor = new UpdateFilmActorInput();
        filmActor.setActorId(999);
        filmActor.setFilmId(999);
        filmActor.setLastUpdate(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(filmActor);

        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            put("/filmActor/actorId=999,filmId=999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(
                new EntityNotFoundException("Unable to update. FilmActor with id=actorId=999,filmId=999 not found.")
            );
    }

    @Test
    public void UpdateFilmActor_FilmActorExists_ReturnStatusOk() throws Exception {
        FilmActorEntity entity = createUpdateEntity();
        entity.setVersiono(0L);

        ActorEntity actor = createActorEntity();
        entity.setActorId(Integer.parseInt(actor.getActorId().toString()));
        entity.setActor(actor);
        FilmEntity film = createFilmEntity();
        entity.setFilmId(Integer.parseInt(film.getFilmId().toString()));
        entity.setFilm(film);
        entity = filmActor_repository.save(entity);
        FindFilmActorByIdOutput output = new FindFilmActorByIdOutput();
        output.setActorId(entity.getActorId());
        output.setFilmId(entity.getFilmId());
        output.setLastUpdate(entity.getLastUpdate());
        output.setVersiono(entity.getVersiono());

        Mockito
            .when(filmActorAppService.findById(new FilmActorId(entity.getActorId(), entity.getFilmId())))
            .thenReturn(output);

        UpdateFilmActorInput filmActorInput = new UpdateFilmActorInput();
        filmActorInput.setActorId(entity.getActorId());
        filmActorInput.setFilmId(entity.getFilmId());
        filmActorInput.setLastUpdate(entity.getLastUpdate());

        ObjectWriter ow = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .writer()
            .withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(filmActorInput);

        mvc
            .perform(
                put("/filmActor/actorId=" + entity.getActorId() + ",filmId=" + entity.getFilmId() + "/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
            )
            .andExpect(status().isOk());

        FilmActorEntity de = createUpdateEntity();
        de.setActorId(entity.getActorId());
        de.setFilmId(entity.getFilmId());
        filmActor_repository.delete(de);
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {
        mvc
            .perform(
                get("/filmActor?search=actorId[equals]=1&limit=10&offset=1").contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void FindAll_SearchIsNotNullAndPropertyIsNotValid_ThrowException() throws Exception {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(
                            get("/filmActor?search=filmActoractorId[equals]=1&limit=10&offset=1")
                                .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
            )
            .hasCause(new Exception("Wrong URL Format: Property filmActoractorId not found!"));
    }

    @Test
    public void GetActor_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/filmActor/actorId999/actor").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid id=actorId999"));
    }

    @Test
    public void GetActor_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/filmActor/actorId=999,filmId=999/actor").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetActor_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get("/filmActor/actorId=" + filmActor.getActorId() + ",filmId=" + filmActor.getFilmId() + "/actor")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }

    @Test
    public void GetFilm_IdIsNotEmptyAndIdIsNotValid_ThrowException() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/filmActor/actorId999/film").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Invalid id=actorId999"));
    }

    @Test
    public void GetFilm_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
        org.assertj.core.api.Assertions
            .assertThatThrownBy(
                () ->
                    mvc
                        .perform(get("/filmActor/actorId=999,filmId=999/film").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
            )
            .hasCause(new EntityNotFoundException("Not found"));
    }

    @Test
    public void GetFilm_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
        mvc
            .perform(
                get("/filmActor/actorId=" + filmActor.getActorId() + ",filmId=" + filmActor.getFilmId() + "/film")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }
}
