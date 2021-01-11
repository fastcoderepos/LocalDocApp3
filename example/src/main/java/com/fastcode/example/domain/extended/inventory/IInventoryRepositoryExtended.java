package com.fastcode.example.domain.extended.inventory;

import com.fastcode.example.domain.core.inventory.IInventoryRepository;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("inventoryRepositoryExtended")
public interface IInventoryRepositoryExtended extends IInventoryRepository {
    //Add your custom code here
}
