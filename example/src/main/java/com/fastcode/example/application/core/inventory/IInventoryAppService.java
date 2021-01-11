package com.fastcode.example.application.core.inventory;

import com.fastcode.example.application.core.inventory.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IInventoryAppService {
    //CRUD Operations

    CreateInventoryOutput create(CreateInventoryInput inventory);

    void delete(Integer id);

    UpdateInventoryOutput update(Integer id, UpdateInventoryInput input);

    FindInventoryByIdOutput findById(Integer id);

    List<FindInventoryByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations

    GetFilmOutput getFilm(Integer inventoryid);

    //Join Column Parsers

    Map<String, String> parseRentalsJoinColumn(String keysString);
}
