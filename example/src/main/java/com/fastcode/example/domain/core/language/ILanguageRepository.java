package com.fastcode.example.domain.core.language;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("languageRepository")
public interface ILanguageRepository
    extends JpaRepository<LanguageEntity, Integer>, QuerydslPredicateExecutor<LanguageEntity> {}
