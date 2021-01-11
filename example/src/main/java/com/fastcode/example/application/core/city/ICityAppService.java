package com.fastcode.example.application.core.city;

import com.fastcode.example.application.core.city.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface ICityAppService {
    //CRUD Operations

    CreateCityOutput create(CreateCityInput city);

    void delete(Integer id);

    UpdateCityOutput update(Integer id, UpdateCityInput input);

    FindCityByIdOutput findById(Integer id);

    List<FindCityByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations

    GetCountryOutput getCountry(Integer cityid);

    //Join Column Parsers

    Map<String, String> parseAddressJoinColumn(String keysString);
}
