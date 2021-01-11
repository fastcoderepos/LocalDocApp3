package com.fastcode.example.domain.core.store;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("storeRepository")
public interface IStoreRepository extends JpaRepository<StoreEntity, Integer>, QuerydslPredicateExecutor<StoreEntity> {}
