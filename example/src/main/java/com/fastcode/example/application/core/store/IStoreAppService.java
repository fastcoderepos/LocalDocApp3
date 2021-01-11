package com.fastcode.example.application.core.store;

import com.fastcode.example.application.core.store.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IStoreAppService {
    //CRUD Operations

    CreateStoreOutput create(CreateStoreInput store);

    void delete(Integer id);

    UpdateStoreOutput update(Integer id, UpdateStoreInput input);

    FindStoreByIdOutput findById(Integer id);

    List<FindStoreByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations
    //Relationship Operations

    GetAddressOutput getAddress(Integer storeid);

    GetStaffOutput getStaff(Integer storeid);
    //Join Column Parsers
}
