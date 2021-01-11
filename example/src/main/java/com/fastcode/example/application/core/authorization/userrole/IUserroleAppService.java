package com.fastcode.example.application.core.authorization.userrole;

import com.fastcode.example.application.core.authorization.userrole.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.domain.core.authorization.userrole.UserroleId;
import java.util.*;
import org.springframework.data.domain.Pageable;

public interface IUserroleAppService {
    //CRUD Operations

    CreateUserroleOutput create(CreateUserroleInput userrole);

    void delete(UserroleId userroleId);

    UpdateUserroleOutput update(UserroleId userroleId, UpdateUserroleInput input);

    FindUserroleByIdOutput findById(UserroleId userroleId);

    List<FindUserroleByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    //Relationship Operations
    //Relationship Operations

    GetRoleOutput getRole(UserroleId userroleId);

    GetUserOutput getUser(UserroleId userroleId);

    //Join Column Parsers

    UserroleId parseUserroleKey(String keysString);
}
