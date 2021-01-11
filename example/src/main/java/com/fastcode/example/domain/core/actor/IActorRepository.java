package com.fastcode.example.domain.core.actor;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("actorRepository")
public interface IActorRepository extends JpaRepository<ActorEntity, Integer>, QuerydslPredicateExecutor<ActorEntity> {}
