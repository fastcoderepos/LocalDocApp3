package com.fastcode.example.domain.extended.authorization.userrole;

import com.fastcode.example.domain.core.authorization.userrole.IUserroleRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("userroleRepositoryExtended")
public interface IUserroleRepositoryExtended extends IUserroleRepository {
    //Add your custom code here
}
