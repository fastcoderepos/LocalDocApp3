package com.fastcode.example.domain.core.authorization.userpermission;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("userpermissionRepository")
public interface IUserpermissionRepository
    extends JpaRepository<UserpermissionEntity, UserpermissionId>, QuerydslPredicateExecutor<UserpermissionEntity> {
    List<UserpermissionEntity> findByUserId(Long userId);
}
