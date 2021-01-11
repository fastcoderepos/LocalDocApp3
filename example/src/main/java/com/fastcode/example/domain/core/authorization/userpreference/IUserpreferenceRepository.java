package com.fastcode.example.domain.core.authorization.userpreference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository("userpreferenceRepository")
public interface IUserpreferenceRepository
    extends JpaRepository<UserpreferenceEntity, Long>, QuerydslPredicateExecutor<UserpreferenceEntity> {}
