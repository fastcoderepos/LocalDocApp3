package com.fastcode.example.application.core.staff;

import com.fastcode.example.application.core.staff.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IStaffAppService {
    //CRUD Operations

    CreateStaffOutput create(CreateStaffInput staff);

    void delete(Integer id);

    UpdateStaffOutput update(Integer id, UpdateStaffInput input);

    FindStaffByIdOutput findById(Integer id);

    List<FindStaffByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations

    GetAddressOutput getAddress(Integer staffid);

    //Join Column Parsers

    Map<String, String> parsePaymentsJoinColumn(String keysString);

    Map<String, String> parseRentalsJoinColumn(String keysString);

    Map<String, String> parseStoresJoinColumn(String keysString);
}
