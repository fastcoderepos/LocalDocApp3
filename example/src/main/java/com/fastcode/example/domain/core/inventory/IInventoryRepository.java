package com.fastcode.example.domain.core.inventory;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("inventoryRepository")
public interface IInventoryRepository
    extends JpaRepository<InventoryEntity, Integer>, QuerydslPredicateExecutor<InventoryEntity> {}
