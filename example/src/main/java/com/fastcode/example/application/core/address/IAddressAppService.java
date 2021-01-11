package com.fastcode.example.application.core.address;

import com.fastcode.example.application.core.address.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IAddressAppService {
    //CRUD Operations

    CreateAddressOutput create(CreateAddressInput address);

    void delete(Integer id);

    UpdateAddressOutput update(Integer id, UpdateAddressInput input);

    FindAddressByIdOutput findById(Integer id);

    List<FindAddressByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations

    GetCityOutput getCity(Integer addressid);

    //Join Column Parsers

    Map<String, String> parseCustomersJoinColumn(String keysString);

    Map<String, String> parseStaffsJoinColumn(String keysString);

    Map<String, String> parseStoresJoinColumn(String keysString);
}
