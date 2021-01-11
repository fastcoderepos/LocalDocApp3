package com.fastcode.example.domain.core.country;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("countryRepository")
public interface ICountryRepository
    extends JpaRepository<CountryEntity, Integer>, QuerydslPredicateExecutor<CountryEntity> {}
