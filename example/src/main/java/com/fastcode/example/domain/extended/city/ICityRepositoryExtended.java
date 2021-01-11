package com.fastcode.example.domain.extended.city;

import com.fastcode.example.domain.core.city.ICityRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("cityRepositoryExtended")
public interface ICityRepositoryExtended extends ICityRepository {
    //Add your custom code here
}
