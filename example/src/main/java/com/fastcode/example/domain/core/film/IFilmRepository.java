package com.fastcode.example.domain.core.film;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("filmRepository")
public interface IFilmRepository extends JpaRepository<FilmEntity, Integer>, QuerydslPredicateExecutor<FilmEntity> {}
