package com.fastcode.example.domain.core.authorization.userpreference;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserpreferenceManager {
    UserpreferenceEntity create(UserpreferenceEntity userpreference);

    void delete(UserpreferenceEntity userpreference);

    UserpreferenceEntity update(UserpreferenceEntity userpreference);

    UserpreferenceEntity findById(Long id);

    Page<UserpreferenceEntity> findAll(Predicate predicate, Pageable pageable);
}
