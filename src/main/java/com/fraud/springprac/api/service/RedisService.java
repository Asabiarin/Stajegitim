package com.fraud.springprac.api.service;

import com.fraud.springprac.api.model.ActiveToken;
import java.util.Optional;

public interface RedisService {
    void saveToken(ActiveToken activeToken, long slidingExpirationMs, long absoluteExpirationMs);
//    Optional<ActiveToken> findValidToken(String token);
    boolean extendTokenIfNotExpired(String token, long slidingExpirationMs);
    void deleteToken(String token);
}