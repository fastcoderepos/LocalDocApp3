package com.fastcode.example.domain.extended.store;

import com.fastcode.example.domain.core.store.IStoreRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("storeRepositoryExtended")
public interface IStoreRepositoryExtended extends IStoreRepository {
    //Add your custom code here
}
