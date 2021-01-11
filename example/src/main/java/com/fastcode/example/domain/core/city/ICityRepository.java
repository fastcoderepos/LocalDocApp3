package com.fastcode.example.domain.core.city;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("cityRepository")
public interface ICityRepository extends JpaRepository<CityEntity, Integer>, QuerydslPredicateExecutor<CityEntity> {}
