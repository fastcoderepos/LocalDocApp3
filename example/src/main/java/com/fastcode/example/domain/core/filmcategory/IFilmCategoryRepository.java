package com.fastcode.example.domain.core.filmcategory;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("filmCategoryRepository")
public interface IFilmCategoryRepository
    extends JpaRepository<FilmCategoryEntity, FilmCategoryId>, QuerydslPredicateExecutor<FilmCategoryEntity> {}
