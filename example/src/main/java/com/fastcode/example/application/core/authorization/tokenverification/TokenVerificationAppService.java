package com.fastcode.example.application.core.authorization.tokenverification;

import com.fastcode.example.domain.core.authorization.tokenverification.ITokenverificationRepository;
import com.fastcode.example.domain.core.authorization.tokenverification.TokenverificationEntity;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.domain.core.authorization.user.UserEntity;
import java.util.Date;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenVerificationAppService implements ITokenVerificationAppService {

    @NonNull
    private final ITokenverificationRepository _tokenRepository;

    @Qualifier("userRepository")
    @NonNull
    private final IUserRepository _userRepository;

    public static final long PASSWORD_TOKEN_EXPIRATION_TIME = 3_600_000; // 1 hour
    public static final long ACCOUNT_VERIFICATION_TOKEN_EXPIRATION_TIME = 86_400_000;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TokenverificationEntity findByTokenAndType(String token, String type) {
        TokenverificationEntity foundToken = _tokenRepository.findByTokenAndTokenType(token, type);

        return foundToken;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TokenverificationEntity findByUserIdAndType(Long userId, String type) {
        TokenverificationEntity foundToken = _tokenRepository.findByUserIdAndTokenType(userId, type);

        return foundToken;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TokenverificationEntity generateToken(String type, Long userId) {
        UserEntity user = _userRepository.findById(userId).orElse(null);
        TokenverificationEntity entity = _tokenRepository.findByUserIdAndTokenType(userId, type);
        if (entity == null) {
            entity = new TokenverificationEntity();
        }
        entity.setTokenType(type);
        entity.setToken(UUID.randomUUID().toString());
        if (type.equalsIgnoreCase("password")) {
            entity.setExpirationTime(new Date(System.currentTimeMillis() + PASSWORD_TOKEN_EXPIRATION_TIME));
        } else if (type.equalsIgnoreCase("registration")) {
            entity.setExpirationTime(new Date(System.currentTimeMillis() + ACCOUNT_VERIFICATION_TOKEN_EXPIRATION_TIME));
        }
        entity.setUserId(user.getId());
        entity.setUser(user);

        return _tokenRepository.save(entity);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void deleteToken(TokenverificationEntity entity) {
        _tokenRepository.delete(entity);
    }
}
