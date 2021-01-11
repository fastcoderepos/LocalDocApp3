package com.fastcode.example.domain.core.authorization.userpreference;

import com.querydsl.core.types.Predicate;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserpreferenceManager implements IUserpreferenceManager {

    @NonNull
    private final IUserpreferenceRepository _userpreferenceRepository;

    public UserpreferenceEntity create(UserpreferenceEntity userpreference) {
        return _userpreferenceRepository.save(userpreference);
    }

    public void delete(UserpreferenceEntity userpreference) {
        _userpreferenceRepository.delete(userpreference);
    }

    public UserpreferenceEntity update(UserpreferenceEntity userpreference) {
        return _userpreferenceRepository.save(userpreference);
    }

    public UserpreferenceEntity findById(Long userId) {
        Optional<UserpreferenceEntity> dbUser = _userpreferenceRepository.findById(userId);
        if (dbUser.isPresent()) {
            UserpreferenceEntity existingUser = dbUser.get();
            return existingUser;
        } else {
            return null;
        }
    }

    public Page<UserpreferenceEntity> findAll(Predicate predicate, Pageable pageable) {
        return _userpreferenceRepository.findAll(predicate, pageable);
    }
}
