package com.fastcode.example.domain.core.authorization.userrole;

import java.time.*;
import java.util.*;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository("userroleRepository")
public interface IUserroleRepository
    extends JpaRepository<UserroleEntity, UserroleId>, QuerydslPredicateExecutor<UserroleEntity> {
    List<UserroleEntity> findByUserId(Long userId);

    List<UserroleEntity> findByRoleId(Long roleId);
}
