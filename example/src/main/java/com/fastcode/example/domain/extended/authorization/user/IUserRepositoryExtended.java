package com.fastcode.example.domain.extended.authorization.user;

import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("userRepositoryExtended")
public interface IUserRepositoryExtended extends IUserRepository {
    //Add your custom code here
}
