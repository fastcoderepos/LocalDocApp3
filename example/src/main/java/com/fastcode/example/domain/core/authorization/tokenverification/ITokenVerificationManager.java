package com.fastcode.example.domain.core.authorization.tokenverification;

import java.time.*;

public interface ITokenVerificationManager {
    TokenverificationEntity save(TokenverificationEntity entity);

    void delete(TokenverificationEntity entity);

    TokenverificationEntity findByTokenAndType(String token, String tokenType);

    TokenverificationEntity findByUserIdAndType(Long userId, String tokenType);
}
